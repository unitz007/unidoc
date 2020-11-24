package org.unidoc;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.ArrayCreationLevel;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.utils.SourceRoot;
import org.unidoc.parse.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main {

    //private static final String FILE_PATH = System.getProperty("user.dir") + "/src/test/java/org/unidocTest/api/package-info.java";
    private static final Path MODULE_PATH = Paths.get(System.getProperty("user.dir") + "/src/test/java/org/unidocTest/api");

    /**
     * parses each compilation unit in the input directory and converts unidoc annotations(in each compilation unit) to javadoc doc comments
     *
     * @param args command-line arguments
     * @throws IOException exception
     */
    public static void main(String... args) throws IOException {
        SourceRoot sourceRoot = new SourceRoot(MODULE_PATH);
        sourceRoot.tryToParse();
        List<CompilationUnit> cu = sourceRoot.getCompilationUnits();
        cu.stream()
                .forEach(compilationUnit -> {
                    VoidVisitorAdapter<Void> declarations;
                    declarations = new PackageParser();
                    declarations.visit(compilationUnit, null);
                    declarations = new InterfaceParser();
                    declarations.visit(compilationUnit, null);
                    declarations = new ClassParser();
                    declarations.visit(compilationUnit, null); // class declaration
                    declarations = new ConstructorParser();
                    declarations.visit(compilationUnit, null);
                    declarations = new FieldParser();
                    declarations.visit(compilationUnit, null); // field declaration
                    declarations = new MethodParser();
                    declarations.visit(compilationUnit, null); // method declaration
                    FileOutputStream fileOutStream;
                    try {
                        fileOutStream = new FileOutputStream(new File(System.getProperty("user.dir") + "/src/test/resources/package-info.java"));
                        fileOutStream.write(compilationUnit.toString().getBytes());
                        fileOutStream.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        for (CompilationUnit c : cu) {
            System.out.println(c);
        }

//        CompilationUnit cu = StaticJavaParser.parse(new File(FILE_PATH));
//        VoidVisitorAdapter<Void> declaration = new PackageParser();
//        declaration.visit(cu, null);
//
//        FileOutputStream fileOutputStream = new FileOutputStream(new File(System.getProperty("user.dir") + "/src/test/resources/package-info.java"));
//        fileOutputStream.write(cu.toString().getBytes());
//        fileOutputStream.close();
    }
}