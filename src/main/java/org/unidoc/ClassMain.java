package org.unidoc;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.ArrayCreationLevel;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.javadoc.Javadoc;
import com.github.javaparser.utils.SourceRoot;
import org.unidoc.parse.*;

import javax.lang.model.SourceVersion;
import javax.tools.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;


public class ClassMain {

    private Logger log = Logger.getLogger(this.toString());

    public void parse(String destination, List<String> classPathList, List<String> fileList, boolean privateRequested, boolean publicRequested) throws IOException {

        List<String> options = new ArrayList<>();
        options.add("-d");
        options.add(destination);
        if (privateRequested == true) {
            options.add("-private");
        } else {
            if (publicRequested == true) {
                options.add("-public");
            } else {
                log.info("no visibility specified.");
            }
        }
        for(String value : options) {
            System.out.println(value);
        }

        classPathList.stream()
                .forEach(srcPath -> {
                    fileList.stream()
                            .forEach(file -> {
                                String path = System.getProperty("user.dir") + srcPath + file;
                                CompilationUnit cu = null;
                                try {
                                    cu = StaticJavaParser.parse(new File(path));
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                VoidVisitorAdapter<Void> declarations = new ClassParser();
                                declarations.visit(cu, null);
                                declarations = new InterfaceParser();
                                declarations.visit(cu, null);
                                declarations = new EnumParser();
                                declarations.visit(cu, null);
                                declarations = new AnnotationParser();
                                declarations.visit(cu, null);
                                declarations = new AnnotationMemberParser();
                                declarations.visit(cu, null);
                                declarations = new ConstructorParser();
                                declarations.visit(cu, null);
                                declarations = new FieldParser();
                                declarations.visit(cu, null); // field declaration
                                declarations = new MethodParser();
                                declarations.visit(cu, null); // method declaration

                                List<ImportDeclaration> finalImportList = new ArrayList<>();
                                NodeList<ImportDeclaration> importDeclarations = cu.getImports();
                                importDeclarations.stream()
                                        .filter(importDeclaration -> !importDeclaration.getNameAsString().endsWith("ModuleDoc"))
                                        .filter(importDeclaration -> !importDeclaration.getNameAsString().endsWith("PackageDoc"))
                                        .filter(importDeclaration -> !importDeclaration.getNameAsString().endsWith("ClassDoc"))
                                        .filter(importDeclaration -> !importDeclaration.getNameAsString().endsWith("InterfaceDoc"))
                                        .filter(importDeclaration -> !importDeclaration.getNameAsString().endsWith("EnumDoc"))
                                        .filter(importDeclaration -> !importDeclaration.getNameAsString().endsWith("AnnotationDoc"))
                                        .filter(importDeclaration -> !importDeclaration.getNameAsString().endsWith("ConstructorDoc"))
                                        .filter(importDeclaration -> !importDeclaration.getNameAsString().endsWith("FieldDoc"))
                                        .filter(importDeclaration -> !importDeclaration.getNameAsString().endsWith("MethodDoc"))
                                        .filter(importDeclaration -> !importDeclaration.getNameAsString().endsWith("AnnotationMemberDoc"))
                                        .filter(importDeclaration -> !importDeclaration.getNameAsString().endsWith("ParamDoc"))
                                        .forEach(importDeclaration -> finalImportList.add(importDeclaration));

                                cu.getImports().clear();
                                for (ImportDeclaration importinList : finalImportList) {
                                    cu.addImport(importinList);
                                }

                                FileOutputStream fileOutputStream = null;
                                try {
                                    fileOutputStream = new FileOutputStream(new File(cu.getStorage().get().getPath().toString()));
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    fileOutputStream.write(cu.toString().getBytes());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    fileOutputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                File fi = new File(cu.getStorage().get().getPath().toString());

                                DocumentationTool tool = ToolProvider.getSystemDocumentationTool();
                                DiagnosticListener<JavaFileObject> diagnosticListener = new DiagnosticListener<>() {
                                    @Override
                                    public void report(Diagnostic<? extends JavaFileObject> diagnostic) {

                                        log.info(diagnostic.getCode());
                                    }
                                };
                                File f = new File(path);
                                System.out.println(f.getAbsolutePath());
                                Optional<CompilationUnit.Storage> optionalStorage = cu.getStorage();
                                System.out.println(optionalStorage.get().getPath().toString());

                                StandardJavaFileManager fileManager = tool.getStandardFileManager(diagnosticListener, null, null);
                                Iterable<? extends JavaFileObject> files = fileManager.getJavaFileObjects(fi);
                                DocumentationTool.DocumentationTask task = tool.getTask(null, fileManager, diagnosticListener, null, options, files);
                                task.call();

                            });
                });


    }
}