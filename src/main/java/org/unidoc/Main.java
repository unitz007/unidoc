package org.unidoc;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
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
        CompilationUnit cu = StaticJavaParser.parse(new File(FILE_PATH));
        VoidVisitorAdapter<Void> field = new FieldParser();
        field.visit(cu, null);
        field = new MethodParser();
        field.visit(cu, null);

        System.out.println(cu);
    }
}