package org.unidoc.parse;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.unidoc.annotations.PackageDoc;
import org.unidoc.core.PackageDocumentation;

/**
 *
 * has method for setting java doc comments and removing @PackageDoc
 */
public class PackageParser extends VoidVisitorAdapter<Void> {

    private final Log log = LogFactory.getLog(this.getClass());

    /**
     * sets java doc comments generated from unidoc @PackageDoc annotation.
     * Also removes @PackageDoc annotations from source code
     *
     * @param pd package to be assessed
     * @param arg void
     */
    @Override
    public void visit(PackageDeclaration pd, Void arg) {
        super.visit(pd, arg);
        PackageDocumentation packageDocumentation = new PackageDocumentation(pd);
        if (pd.isAnnotationPresent(PackageDoc.class)) {
            pd.setComment(packageDocumentation.getJavadoc());
            pd.getAnnotationByClass(PackageDoc.class).ifPresent(Node::remove);
        } else {
            log.info(pd.getNameAsString() + " is not annotated with @" + PackageDoc.class.getSimpleName());
        }
    }
}
