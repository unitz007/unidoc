package org.unidoc.docgen;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.unidoc.parse.*;

import javax.tools.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 *
 * has methods for parsing packages and classes(cli args)
 */
public class UnitParser {

    private Logger log = Logger.getLogger(this.toString());

    /**
     *
     * parses compilation units in package cli arg
     *
     * @param opts options list with -d and its' value, plus -sourcepath without its' value
     * @param compilationUnit a class in the package
     * @param privateRequested was -private used
     * @param publicRequested was -public used
     * @param packageRequested was -package used
     */
    public void packageGenParser(List<String> opts, CompilationUnit compilationUnit, boolean privateRequested, boolean publicRequested, boolean packageRequested) {

        List<String> options = opts;

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
                    log.info("default access used -> -protected");
                }
            }
        }
        for (String value : options) {
            System.out.println(value);
        }
        File file = new File(compilationUnit.getStorage().get().getPath().toString());

        DocumentationTool tool = ToolProvider.getSystemDocumentationTool();

//                            Optional<CompilationUnit.Storage> optionalStorage = compilationUnit.getStorage();
//                            System.out.println(optionalStorage.get().getPath().toString());

        StandardJavaFileManager fileManager = tool.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> javadocCompilationUnit = fileManager.getJavaFileObjects(file);

        DocumentationTool.DocumentationTask task = tool.getTask(null, fileManager, null, null, options, javadocCompilationUnit);
        task.call();

        if (options.size() == 5) { //when an access option is used
            options.remove(4);
            options.remove(3);
        } else if (options.size() == 3) { //when an access option is used in absence of -sourcepath
            options.remove(2);
        } else if (options.size() == 4) { //when no access option is used
            options.remove(3);
        }

    }

    /**
     *
     * parses class cli arg
     *
     * @param opts options list with -d and its' value
     * @param compilationUnit class to document
     * @param privateRequested was -private used
     * @param publicRequested was -public used
     * @param packageRequested was -package used
     */
    public void classGenParser(List<String> opts, CompilationUnit compilationUnit, boolean privateRequested, boolean publicRequested, boolean packageRequested) {

        List<String> options = opts;

        VoidVisitorAdapter<Void> declarations = new ClassParser();
        declarations.visit(compilationUnit, null);
        declarations = new PackageParser();
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
        declarations.visit(compilationUnit, null); // field declaration
        declarations = new MethodParser();
        declarations.visit(compilationUnit, null); // method declaration

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
                    log.info("default access used -> -protected");
                }
            }
        }

        for (String value : options) {
            System.out.println(value);
        }

        File file = new File(compilationUnit.getStorage().get().getPath().toString());
        Optional<CompilationUnit.Storage> cuStorage = compilationUnit.getStorage();
        System.out.println(cuStorage.get().getPath().toString());

        DocumentationTool tool = ToolProvider.getSystemDocumentationTool();

        StandardJavaFileManager fileManager = tool.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> javadocCompilationUnit = fileManager.getJavaFileObjects(file);
        DocumentationTool.DocumentationTask task = tool.getTask(null, fileManager, null, null, options, javadocCompilationUnit);
        task.call();
    }
}
