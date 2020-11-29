package org.unidoc.docgen;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.utils.SourceRoot;
import org.unidoc.parse.*;

import javax.tools.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;

/**
 *
 * has methods for generating javadoc html file(s) for packages
 */
public class PackageDocGen {

    private Logger log = Logger.getLogger(this.toString());

    /**
     *
     * calls method that generates javadoc html files for package(s) when current directory is considered
     *
     * @param destination where to store generated html file(s)
     * @param sourcePathList path(s) to package(s) to document
     * @param packageList package(s) to document
     * @param privateRequested whether to include all declarations in generated doc file(s)
     * @param publicRequested whether to include only public declarations in generated doc file(s)
     * @param packageRequested whether to include only public, protected and package declarations in generated doc file(s)
     */
    public void parsePackageFromCurrentDirectory(String destination, List<String> sourcePathList, List<String> packageList, boolean privateRequested, boolean publicRequested, boolean packageRequested) {

        List<String> options = new ArrayList<>();
        if (!(destination == null)) {
            options.add("-d");
            options.add(destination);
        } else {
            log.info("doc html would be stored at default location");
        }

        //ran from current working directory
        if (!(sourcePathList == null)) {

            sourcePathList.stream().forEach(sourcepath -> {

                packageList.stream().forEach(pack -> {
                    String finalPackage = pack.replace(".", "/");
                    Path path = Paths.get(System.getProperty("user.dir") + sourcepath + "/" + finalPackage);

                    try {
                        SourceRoot sourceRoot = new SourceRoot(path);
                        sourceRoot.tryToParse();
                        List<CompilationUnit> cu = sourceRoot.getCompilationUnits();

                        //cu leaves
                        cu.stream().forEach(compilationUnit -> {
                            options.add("-sourcepath");
                            options.add(sourcepath);
                            UnitParser up = new UnitParser();
                            up.packageUnitParser(options, compilationUnit, privateRequested, publicRequested, packageRequested);

                        });
                    } catch (IllegalArgumentException | IOException iae) {
                        log.info("Not found. \nNOTE: Ignore if command args was a source file or source files. \n Otherwise, package(s) not found.");
                        return; //works
                    }
                });
            });
        } else { //document a package without -sourcepath i.e from working directory
            packageList.stream().forEach(pack -> {
                String finalPack = pack.replace(".", "/");
                Path path = Paths.get(System.getProperty("user.dir") + finalPack);
                System.out.println(path.toString());
                try {
                    SourceRoot sourceRoot = new SourceRoot(path);
                    sourceRoot.tryToParse();

                    List<CompilationUnit> cu = sourceRoot.getCompilationUnits();
                    cu.stream().forEach(compilationUnit -> {
                        VoidVisitorAdapter<Void> declarations = new PackageParser();
                        declarations.visit(compilationUnit, null);
                        declarations = new ClassParser();
                        declarations.visit(compilationUnit, null);
                        declarations = new InterfaceParser();
                        declarations.visit(compilationUnit, null);
                        declarations = new EnumParser();
                        declarations.visit(compilationUnit, null);
                        declarations = new AnnotationParser();
                        declarations.visit(compilationUnit, null);
                        declarations = new AnnotationMemberParser();
                        declarations.visit(compilationUnit, null);
                        declarations = new ConstructorParser();
                        declarations.visit(compilationUnit, null);
                        declarations = new FieldParser();
                        declarations.visit(compilationUnit, null);
                        declarations = new MethodParser();
                        declarations.visit(compilationUnit, null);

                        List<ImportDeclaration> finalImportList = new ArrayList<>();
                        NodeList<ImportDeclaration> importDeclarations = compilationUnit.getImports();
                        importDeclarations.stream()
                                .filter(importDeclaration -> !importDeclaration.getNameAsString().contains("unidoc.annotations"))
                                .forEach(importDeclaration -> finalImportList.add(importDeclaration));
                        compilationUnit.getImports().clear();
                        for (ImportDeclaration importinList : finalImportList) {
                            compilationUnit.addImport(importinList);
                        }
                        FileOutputStream fileOutputStream = null;
                        try {
                            fileOutputStream = new FileOutputStream(new File(compilationUnit.getStorage().get().getPath().toString()));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        try {
                            assert fileOutputStream != null;
                            fileOutputStream.write(compilationUnit.toString().getBytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (privateRequested) {
                            options.add("-private");
                        } else {
                            if (publicRequested) {
                                options.add("-public");
                            } else {
                                if (packageRequested) {
                                    options.add("-package");
                                } else {
                                    log.info("default visibility used.");
                                }
                            }
                        }
                        for (String value : options) {
                            System.out.println(value);
                        }
                        File file = new File(compilationUnit.getStorage().get().getPath().toString());

                        DocumentationTool tool = ToolProvider.getSystemDocumentationTool();
                        DiagnosticListener<JavaFileObject> diagnosticListener = Diagnostic::getCode;

//                            Optional<CompilationUnit.Storage> optionalStorage = compilationUnit.getStorage();
//                            System.out.println(optionalStorage.get().getPath().toString());

                        StandardJavaFileManager fileManager = tool.getStandardFileManager(diagnosticListener, null, null);
                        Iterable<? extends JavaFileObject> javadocCompilationUnit = fileManager.getJavaFileObjects(file);

                        DocumentationTool.DocumentationTask task = tool.getTask(null, fileManager, diagnosticListener, null, options, javadocCompilationUnit);
                        task.call();

                    });
                } catch (IllegalArgumentException | IOException iae) {
                    log.info("Not found. \nNOTE: Ignore if command args was a source file or source files. \n Otherwise, package(s) not found.");
                    return; //works
                }
            });
        }
    }

    /**
     *
     * calls method that generates javadoc html files for package(s) when the current directory isn't considered
     *
     * @param destination where to store generated html file(s)
     * @param sourcePathList path(s) to package(s) to document
     * @param packageList package(s) to document
     * @param privateRequested whether to include all declarations in generated doc file(s)
     * @param publicRequested whether to include only public declarations in generated doc file(s)
     * @param packageRequested whether to include only public, protected and package declarations in generated doc file(s)
     */
    public void parsePackageFromAnyDirectory(String destination, List<String> sourcePathList, List<String> packageList, boolean privateRequested, boolean publicRequested, boolean packageRequested) {

        List<String> options = new ArrayList<>();
        if (!(destination == null)) {
            options.add("-d");
            options.add(destination);
        } else {
            log.info("doc would be stored at default location");
        }

        if (!(sourcePathList == null)) {

            sourcePathList.stream().forEach(sourcepath -> {
                options.add("-sourcepath");
                options.add(sourcepath);

                packageList.stream().forEach(pack -> {
                    String finalPack = pack.toString().replace(".", "/");
                    Path path = Paths.get(sourcepath + "/" + finalPack);
                    try {
                        SourceRoot sourceRoot = new SourceRoot(path);
                        sourceRoot.tryToParse();

                        List<CompilationUnit> cu = sourceRoot.getCompilationUnits();
                        cu.stream().forEach(compilationUnit -> {
                            UnitParser up = new UnitParser();
                            up.packageUnitParser(options, compilationUnit, privateRequested, publicRequested, packageRequested);

                        });
                    } catch (IllegalArgumentException | IOException iae) {
                        log.info("Not found. \nNOTE: Ignore if command args was a source file or source files. \n Otherwise, package(s) not found.");
                        return; //works
                    }
                });
            });
        } else {
            return;
        }
    }
}
