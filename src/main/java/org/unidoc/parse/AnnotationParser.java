package org.unidoc.parse;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.unidoc.annotations.AnnotationDoc;
import org.unidoc.core.AnnotationDocumentation;

/**
 *
 * has method for setting java doc comments and removing @AnnotationDoc
 */
public class AnnotationParser extends VoidVisitorAdapter<Void> {

    private final Log log = LogFactory.getLog(this.getClass());

    /**
     * sets java doc comments generated from unidoc @AnnotationDoc annotation.
     * Also removes @AnnotationDoc annotations from source code
     *
     * @param ad annotation to be accessed
     * @param arg void
     */
    @Override
    public void visit(AnnotationDeclaration ad, Void arg) {
        super.visit(ad, arg);
        if (ad.isAnnotationPresent(AnnotationDoc.class)) {
            AnnotationDocumentation annotationDocumentation = new AnnotationDocumentation(ad);
            ad.setJavadocComment(annotationDocumentation.getJavadoc());
            ad.getAnnotationByClass(AnnotationDoc.class).ifPresent(Node::remove);
        } else {
            log.info(ad.getNameAsString() + " is not annotated with @" + AnnotationDoc.class.getSimpleName());
        }
    }
}
