package org.unidoc.cli;

import org.unidoc.docgen.ClassGen;
import org.unidoc.docgen.PackageGen;
import picocli.CommandLine;

import java.util.List;

/**
 *
 * unidoc cli class
 */
@CommandLine.Command(name = "unidoc", mixinStandardHelpOptions = true, version = "unidoc 1.0", separator = " ", description = "Generates java source code documentation from unidoc annotations")
public class UnidocCli implements Runnable {

    /**
     * where output html would be stored
     */
    @CommandLine.Option(names = { "-d" }, arity = "1", description = "indicates directory of output file(s)")
    String destination;

    /**
     * path to find packages
     */
    @CommandLine.Option(names = { "-sourcepath", "--source-path" }, arity = "1", split = ":", splitSynopsisLabel = ":", description = "marker of src path(s)")
    List<String> sourcePaths;

    /**
     * package(s) to be documented
     */
    @CommandLine.Parameters(paramLabel = "PackageAndClass", description = "package(s) and class(es) to document")
    List<String> packageAndClassList;

    /**
     * whether to document all declarations
     */
    @CommandLine.Option(names = { "-private" }, description = "indicates that all classes should be documented")
    boolean privateRequested;

    /**
     * whether to document only public declarations
     */
    @CommandLine.Option(names = { "-public" }, description = "indicates that only public declarations should be documented")
    boolean publicRequested;

    /**
     * whether to document public, protected and package declarations
     */
    @CommandLine.Option(names = { "-package" }, description = "indicates that only public, protected and package declarations should be documented")
    boolean packageRequested;

    /**
     *
     * passes options and parameters to methods that generate javadoc html files
     */
    @Override
    public void run() {

        PackageGen packageGen = new PackageGen();
        packageGen.parsePackage(destination, sourcePaths, packageAndClassList, privateRequested, publicRequested, packageRequested);

        packageAndClassList.stream()
                .filter(list -> list.endsWith(".java"))
                .forEach(clazz -> {
                    ClassGen classGen = new ClassGen();
                    classGen.parseClass(destination, clazz, privateRequested, publicRequested, packageRequested);
                });
    }

    public static void main(String[] args) {

        CommandLine cmd = new CommandLine(new UnidocCli());
        cmd.execute("-d", "/home/david/doc", "/src/test/java/org/unidocTest/api/TestClass.java", "/src/test/java/org/unidocTest/api/T.java", "-private");
    }
}
