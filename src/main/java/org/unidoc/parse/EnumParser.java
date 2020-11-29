package org.unidoc.parse;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.unidoc.annotations.EnumDoc;
import org.unidoc.core.EnumDocumentation;

/**
 *
 * has method for setting java doc comments and removing @EnumDoc
 */
public class EnumParser extends VoidVisitorAdapter<Void> {

    private final Log log = LogFactory.getLog(this.getClass());

    /**
     * sets java doc comments generated from unidoc @EnumDoc annotation.
     * Also removes @EnumDoc annotations from source code
     *
     * @param ed enum to be assessed
     * @param arg void
     */
    @Override
    public void visit(EnumDeclaration ed, Void arg) {
        super.visit(ed, arg);
        if (ed.isAnnotationPresent(EnumDoc.class)) {
            EnumDocumentation enumDocumentation = new EnumDocumentation(ed);
            ed.setJavadocComment(enumDocumentation.getJavadoc());
            ed.getAnnotationByClass(EnumDoc.class).ifPresent(Node::remove);
        } else {
            log.info(ed.getNameAsString() + " is not annotated with @" + EnumDoc.class.getSimpleName());
        }
    }
}
