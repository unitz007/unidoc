package org.unidoc;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.unidoc.parse.ClassParser;
import org.unidoc.parse.FieldParser;
import org.unidoc.parse.MethodParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class Main {

    private static final String FILE_PATH = System.getProperty("user.dir") + "/src/test/java/org/unidocTest/TestClass.java";

    /**
     *
     * @param args command-line arguments
     * @throws IOException exception
     */
    public static void main(String... args) throws IOException {
        File file = new File(FILE_PATH);
        CompilationUnit cu = StaticJavaParser.parse(file);
        VoidVisitorAdapter<Void> field = new FieldParser();
        field.visit(cu, null);
        field = new MethodParser();
        field.visit(cu, null);
        field = new ClassParser();
        field.visit(cu, null);
        FileOutputStream fileOutStream = new FileOutputStream(
                new File(System.getProperty("user.dir") + "/src/test/resources/TestClass.java"));
        fileOutStream.write(cu.toString().getBytes());
    }
}