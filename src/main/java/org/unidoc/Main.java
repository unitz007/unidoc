package org.unidoc;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.javadoc.JavadocBlockTag;
import org.unidoc.parse.FieldParser;
import org.unidoc.parse.MethodParser;

import java.io.*;
import java.util.Scanner;

@ClassDoc(author = "Dinneya Charles", version = "1.0.0", value = "")
public class Main {

    private static final String FILE_PATH = System.getProperty("user.dir") + "/src/test/java/org/unidocTest/TestClass.java";

    /**
     *
     * @param args command-line arguments
     * @throws FileNotFoundException exception
     */
    public static void main(String... args) throws IOException {
        File file = new File(FILE_PATH);
        CompilationUnit cu = StaticJavaParser.parse(file);
        VoidVisitorAdapter<Void> field = new FieldParser();
        field.visit(cu, null);
        FileOutputStream fileOutStream = new FileOutputStream(
                new File(System.getProperty("user.dir") + "/src/test/resources/TestClass.java"));
        fileOutStream.write(cu.toString().getBytes());
    }
}