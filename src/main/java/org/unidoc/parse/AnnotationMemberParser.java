package org.unidoc.parse;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.AnnotationMemberDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.unidoc.annotations.AnnotationMemberDoc;
import org.unidoc.core.AnnotationMemberDocumentation;

public class AnnotationMemberParser extends VoidVisitorAdapter<Void> {

    private final Log log = LogFactory.getLog(this.getClass());

    /**
     * Converts unidoc @AnnotationMemberDoc to javadoc comments.
     * Also removes @AnnotationMemberDoc annotations from source code
     *
     * @param aed annotationMember to be assessed
     * @param arg void
     */
    @Override
    public void visit(AnnotationMemberDeclaration aed, Void arg) {
        super.visit(aed, arg);

        if(aed.isAnnotationPresent(AnnotationMemberDoc.class)) {
            AnnotationMemberDocumentation annotationMemberDocumentation = new AnnotationMemberDocumentation(aed);
            aed.setJavadocComment(annotationMemberDocumentation.getJavadoc());
            aed.getAnnotationByClass(AnnotationMemberDoc.class).ifPresent(Node::remove);
        } else {
            log.info(aed.getNameAsString() + " is not annotated with @" + AnnotationMemberDoc.class.getSimpleName());
        }
    }
}
