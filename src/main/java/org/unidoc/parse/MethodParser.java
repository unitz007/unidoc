package org.unidoc.parse;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.unidoc.MethodDoc;
import org.unidoc.core.MethodDocumentation;

public class MethodParser extends VoidVisitorAdapter<Void> {

    private final Log log = LogFactory.getLog(this.getClass()); // logger

    /**
     * parses method.
     * @param md Method to be accessed
     * @param arg void
     */
    public void visit(MethodDeclaration md, Void arg) {
        super.visit(md, arg);
        if (md.isAnnotationPresent(MethodDoc.class)) { // check if method has @MethodDoc annotation
            MethodDocumentation methodDocumentation = new MethodDocumentation(md);
            md.setJavadocComment(methodDocumentation.getJavadoc());
            md.getAnnotationByClass(MethodDoc.class).ifPresent(Node::remove);
        } else {
            log.info(md.getNameAsString() + "() is not annotated with @" + MethodDoc.class.getSimpleName());
        }
    }
}