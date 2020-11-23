package org.unidoc;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.ArrayCreationLevel;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.utils.SourceRoot;
import org.unidoc.parse.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main {

//    private static final String FILE_PATH = System.getProperty("user.dir") + "/src/test/java/org/unidocTest/TestClass.java";
    private static final Path MODULE_PATH = Paths.get(System.getProperty("user.dir") + "/src/test/java/org/unidocTest/api");

    /**
     *
     *
     * @param args command-line arguments
     * @throws IOException exception
     */
    public static void main(String... args) throws IOException {
        SourceRoot sourceRoot = new SourceRoot(MODULE_PATH);
        sourceRoot.tryToParse();
        List<CompilationUnit> cu = sourceRoot.getCompilationUnits();

        for (CompilationUnit c : cu) {
            VoidVisitorAdapter<Void> field;
            field = new PackageParser();
            field.visit(c, null);
            field = new FieldParser();
            field.visit(c, null); // field declaration
            field = new MethodParser();
            field.visit(c, null); // method declaration
            field = new ClassParser();
            field.visit(c, null); // class declaration
            field = new InterfaceParser();
            field.visit(c, null);
            field = new ConstructorParser();
            field.visit(c, null);
        }

        FileOutputStream fileOutStream = new FileOutputStream(new File(System.getProperty("user.dir") + "/src/test/resources/TestClass.java"));
        fileOutStream.write(cu.toString().getBytes());
        fileOutStream.close();
        System.out.println(cu);

    }
}