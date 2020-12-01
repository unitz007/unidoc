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
 * has method for generating javadoc html file for class cli arg
 */
public class ClassGen {

    private Logger log = Logger.getLogger(this.toString());

    /**
     *
     * calls method that generates javadoc html files for class arg
     *
     * @param destination where to store generated html file
     * @param clazz class to document
     * @param privateRequested whether to include all declarations in generated doc file
     * @param publicRequested whether to include only public declarations in generated doc file
     * @param packageRequested whether to include only public, protected and package declarations in generated doc file
     */
    public void parseClass(String destination, String clazz, boolean privateRequested, boolean publicRequested, boolean packageRequested) {

        List<String> options = new ArrayList<>();
        if (!(destination == null)) {
        options.add("-d");
        options.add(destination);
        } else {
            log.info("file would be stored at default location");
        }

        String pathToClass = System.getProperty("user.dir") + clazz;
        CompilationUnit cu = null;
        UnitParser up = new UnitParser();
        try {
            cu = StaticJavaParser.parse(new File(pathToClass));
            up.classGenParser(options, cu, privateRequested, publicRequested, packageRequested);

        } catch (FileNotFoundException e) {
            try {
                cu = StaticJavaParser.parse(new File(clazz));
                up.classGenParser(options, cu, privateRequested, publicRequested, packageRequested);
            } catch (FileNotFoundException ee) {
                log.info("null");
            }
        }
    }
}
