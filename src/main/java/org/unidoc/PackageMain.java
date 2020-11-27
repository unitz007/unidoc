package org.unidoc;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.utils.SourceRoot;
import org.unidoc.parse.*;

import javax.tools.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

public class PackageMain {

//    /**
//     * parses each compilation unit in the input directory and converts unidoc annotations(in each compilation unit) to javadoc doc comments
//     *
//     * @param args command-line arguments
//     * @throws IOException exception
//     */
    public void parse(List<String> packagePathList, List<String> packageList) throws IOException, IOException {

        packagePathList.stream()
                .forEach(srcPath -> {
                    packageList.stream()
                            .forEach(pack -> {
                                Path path = Paths.get(System.getProperty("user.dir")+ srcPath + pack);
                                SourceRoot sourceRoot = new SourceRoot(path);
                                try {
                                    sourceRoot.tryToParse();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                List<CompilationUnit> cu = sourceRoot.getCompilationUnits();
                                cu.stream()
                                        .forEach(compilationUnit -> {
                                            VoidVisitorAdapter<Void> declarations;
                                            declarations = new ModuleParser();
                                            declarations.visit(compilationUnit, null);
                                            declarations = new PackageParser();
                                            declarations.visit(compilationUnit, null);
                                            declarations = new InterfaceParser();
                                            declarations.visit(compilationUnit, null);
                                            declarations = new ClassParser();
                                            declarations.visit(compilationUnit, null); // class declaration
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

                                            DocumentationTool tool = ToolProvider.getSystemDocumentationTool();
                                            DiagnosticListener<JavaFileObject> diagnosticListener = new DiagnosticListener<JavaFileObject>() {
                                                @Override
                                                public void report(Diagnostic<? extends JavaFileObject> diagnostic) {
                                                    Logger log = Logger.getLogger(this.toString());
                                                    log.info(diagnostic.getCode().toString());
                                                }
                                            };

                                            StandardJavaFileManager fileManager = tool.getStandardFileManager(null, null, null);
                                            Iterable<? extends JavaFileObject> files = fileManager.getJavaFileObjects(compilationUnit.toString());
                                            DocumentationTool.DocumentationTask task = tool.getTask(null, fileManager, diagnosticListener, null, null, files);
                                            task.call();
                                        });
                            });
                });

    }
}
