package org.unidoc.docgen;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * has method for generating javadoc html file(s) for classes
 */
public class ClassDocGen {

    private Logger log = Logger.getLogger(this.toString());

    /**
     *
     * calls method that generates javadoc html files for class(es)
     *
     * @param destination where to store generated html file(s)
     * @param classesList class(es) to document
     * @param privateRequested whether to include all declarations in generated doc file(s)
     * @param publicRequested whether to include only public declarations in generated doc file(s)
     * @param packageRequested whether to include only public, protected and package declarations in generated doc file(s)
     */
    public void parseClassFromCurrentDirectory(String destination, List<String> classesList, boolean privateRequested, boolean publicRequested, boolean packageRequested) {

    List<String> options = new ArrayList<>();
    if (!(destination == null)) {
    options.add("-d");
    options.add(destination);
    } else {
        log.info("doc html would be stored at default location");
    }

    //from source directory
    classesList.stream().forEach(sourceFile -> {
        String pathToClass = System.getProperty("user.dir") + sourceFile;
        CompilationUnit cu = null;
        UnitParser up = new UnitParser();
        try {
            cu = StaticJavaParser.parse(new File(pathToClass));
            up.classUnitParser(options, cu, privateRequested, publicRequested, packageRequested);

        } catch (FileNotFoundException e) {
            try {
                cu = StaticJavaParser.parse(new File(sourceFile));
                up.classUnitParser(options, cu, privateRequested, publicRequested, packageRequested);
            } catch (FileNotFoundException ee) {
                 log.info("source file not found. \nNOTE: Ignore if command args was a package or packages without a source file. \n Otherwise, the source file doesn't exist.");
            }
        } });
    }
}
