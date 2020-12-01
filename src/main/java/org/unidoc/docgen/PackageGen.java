package org.unidoc.docgen;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.SourceRoot;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * has method for generating javadoc html file for package cli arg
 */
public class PackageGen {

    private Logger log = Logger.getLogger(this.toString());

    /**
     *
     * calls method that generates javadoc html files for package cli arg
     *
     * @param destination where to store generated html file
     * @param sourcePathList path to package to document
     * @param packageList package to document
     * @param privateRequested whether to include all declarations in generated doc file
     * @param publicRequested whether to include only public declarations in generated doc file
     * @param packageRequested whether to include only public, protected and package declarations in generated doc file
     */
    public void parsePackage(String destination, List<String> sourcePathList, List<String> packageList, boolean privateRequested, boolean publicRequested, boolean packageRequested) {

        List<String> options = new ArrayList<>();
        if (!(destination == null)) {
            options.add("-d");
            options.add(destination);
        } else {
            log.info("file would be stored at default location");
        }

        if (!(sourcePathList == null)) {

            sourcePathList.stream().forEach(sourcepath -> {

                packageList.stream().forEach(pack -> {
                    options.add("-sourcepath");
                    String finalPackage = pack.replace(".", "/");
                    Path path = Paths.get(System.getProperty("user.dir") + sourcepath + "/" + finalPackage);
                    System.out.println(path.toString());

                    try {
                        SourceRoot sourceRoot = new SourceRoot(path);
                        sourceRoot.tryToParse();
                        List<CompilationUnit> cu = sourceRoot.getCompilationUnits();

                        cu.stream().forEach(compilationUnit -> {

                            options.add(sourcepath);
                            UnitParser up = new UnitParser();
                            up.packageGenParser(options, compilationUnit, privateRequested, publicRequested, packageRequested);

                        });
                    } catch (IllegalArgumentException | IOException iae) {
                        //probably ran from a different working directory
                        String finalPackage1 = pack.replace(".", "/");
                        Path path1 = Paths.get(sourcepath + "/" + finalPackage1);
                        System.out.println(path1.toString());
                        try {
                            SourceRoot sourceRoot = new SourceRoot(path1);
                            sourceRoot.tryToParse();

                            List<CompilationUnit> cu = sourceRoot.getCompilationUnits();
                            cu.stream().forEach(compilationUnit -> {

                                options.add(sourcepath);
                                UnitParser up = new UnitParser();
                                up.packageGenParser(options, compilationUnit, privateRequested, publicRequested, packageRequested);

                            });
                        } catch (IllegalArgumentException | IOException e) {
                            log.info("null");
                        }

                    }
                });
            });
        } else { //document a package without -sourcepath i.e from working directory

            packageList.stream().forEach(pack -> {
                String finalPack = pack.replace(".", "/");
                Path path = Paths.get(System.getProperty("user.dir") + finalPack);
                System.out.println(path.toString());
                try {
                    SourceRoot sourceRoot = new SourceRoot(path);
                    sourceRoot.tryToParse();

                    List<CompilationUnit> cu = sourceRoot.getCompilationUnits();
                    cu.stream().forEach(compilationUnit -> {

                        UnitParser up = new UnitParser();
                        up.packageGenParser(options, compilationUnit, privateRequested, publicRequested, packageRequested);

                    });
                } catch (IllegalArgumentException | IOException e) {
                    log.info("null");
                }
            });
        }
    }
}

