package org.unidoc.docgen;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.unidoc.parse.*;

import javax.tools.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * has methods for parsing packages and classes
 */
public class UnitParser {

    private Logger log = Logger.getLogger(this.toString());

    /**
     *
     * parses compilation units in packages
     *
     * @param opts
     * @param compilationUnit
     * @param privateRequested
     * @param publicRequested
     * @param packageRequested
     */
    public void packageUnitParser(List<String> opts, CompilationUnit compilationUnit, boolean privateRequested, boolean publicRequested, boolean packageRequested) {

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

        options.remove(3);
        options.remove(2);
    }

    /**
     *
     * parses classes/compilations units
     *
     * @param opts
     * @param compilationUnit
     * @param privateRequested
     * @param publicRequested
     * @param packageRequested
     */
    public void classUnitParser(List<String> opts, CompilationUnit compilationUnit, boolean privateRequested, boolean publicRequested, boolean packageRequested) {

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
//        File f = new File(path);
//        System.out.println(f.getAbsolutePath());
//        Optional<CompilationUnit.Storage> optionalStorage = cu.getStorage();
//        System.out.println(optionalStorage.get().getPath().toString());

        StandardJavaFileManager fileManager = tool.getStandardFileManager(diagnosticListener, null, null);
        Iterable<? extends JavaFileObject> javadocCompilationUnit = fileManager.getJavaFileObjects(file);
        DocumentationTool.DocumentationTask task = tool.getTask(null, fileManager, diagnosticListener, null, options, javadocCompilationUnit);
        task.call();
    }
}
