package org.unidoc.parse;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.unidoc.annotations.AnnotationDoc;
import org.unidoc.annotations.EnumDoc;
import org.unidoc.core.AnnotationDocumentation;
import org.unidoc.core.EnumDocumentation;

public class AnnotationParser extends VoidVisitorAdapter<Void> {

    private final Log log = LogFactory.getLog(this.getClass());

    /**
     * Converts unidoc @AnnotationDoc to javadoc comments.
     * Also removes @AnnotationDoc annotations from source code
     *
     * @param ad annotation to be accessed
     * @param arg void
     */
    @Override
    public void visit(AnnotationDeclaration ad, Void arg) {
        super.visit(ad, arg);

        if(ad.isAnnotationPresent(AnnotationDoc.class)) {
            AnnotationDocumentation annotationDocumentation = new AnnotationDocumentation(ad);
            ad.setJavadocComment(annotationDocumentation.getJavadoc());
            ad.getAnnotationByClass(AnnotationDoc.class).ifPresent(Node::remove);
        } else {
            log.info(ad.getNameAsString() + " is not annotated with @" + AnnotationDoc.class.getSimpleName());
        }
    }
}
