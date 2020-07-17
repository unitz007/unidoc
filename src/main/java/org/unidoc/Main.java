package org.unidoc;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.unidoc.parse.FieldParser;
import org.unidoc.parse.MethodParser;

import java.io.File;
import java.io.FileNotFoundException;

@ClassDoc(author = "Dinneya Charles", version = "1.0.0", value = "")
public class Main {

    private static final String FILE_PATH = "/home/charles/projects/unidoc/src/test/java/org/unidocTest/TestClass.java";

    /**
     *
     * @param args command-line arguments
     * @throws FileNotFoundException exception
     */
    public static void main(String... args) throws FileNotFoundException {
        File file = new File(FILE_PATH);
        CompilationUnit cu = StaticJavaParser.parse(file);
        VoidVisitorAdapter<Void> field = new MethodParser();
        field.visit(cu, null);
    }
}