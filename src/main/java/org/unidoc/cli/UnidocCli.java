package org.unidoc.cli;

import com.github.javaparser.ast.body.AnnotationDeclaration;
import org.unidoc.ClassMain;
import org.unidoc.PackageMain;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@CommandLine.Command(name = "unidoc", mixinStandardHelpOptions = true, description = "generates java source code documentation with annotations")
public class UnidocCli implements Runnable {

    /**
     * path of output file(s)
     */
    @CommandLine.Option(names = {"-d", "-destination"}, arity = "1", description = "marker of directory of output file(s)")
    String destination;

    /**
     * path of src. Equivalent of -sourcepath
     */
    @CommandLine.Option(names = {"-srcPath"}, arity = "1..*", description = "marker of src path(s)")
    List<String> srcPaths;

    /**
     * file(s) to be documented
     */
    @CommandLine.Option(names = {"-f", "-file"}, arity = "1..*", description = "marker of file(s) to document")
    List<String> files;

    /**
     * package(s) to be documented
     */
    @CommandLine.Option(names = {"-p"}, arity = "1..*", description = "marker of package(s) to document")
    List<String> packages;

    /**
     * represents javadoc -exclude
     */
    @CommandLine.Option(names = {"-exclude"}, description = "marker of package(s) to exclude")
    List<String> excludedPackages;

    /**
     * whether to document all declarations
     */
    @CommandLine.Option(names = {"-private"}, description = "marker to document all classes")
    boolean privateRequested;

    /**
     * whether to document only public declarations
     */
    @CommandLine.Option(names = {"-public"}, description = "marker to document only public declarations")
    boolean publicRequested;

    /**
     * whether to document public, protected and package declarations
     *
     */
    @CommandLine.Option(names = {"-package"}, description = "marker to document public, protected and package declarations")
    boolean packageRequested;

    @Override
    public void run() {

        if (!(files == null)) {
            ClassMain classMain = new ClassMain();
            try {
                classMain.parse(destination, srcPaths, files, privateRequested, publicRequested);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (!(packages == null)) {
                PackageMain packageMain = new PackageMain();
                try {
                    packageMain.parse(srcPaths, packages);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                return;
            }
        }
    }

    public static void main(String[] args) {
        int cmd = new CommandLine(new UnidocCli()).execute("-d", "/home/david/jquery","-srcPath", "/src/test/java/org/unidocTest/api/", "-f", "TestClass.java");
    }


}
