package org.unidoc.parse;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.unidoc.annotations.ConstructorDoc;
import org.unidoc.core.ConstructorDocumentation;

/**
 *
 * has method for setting java doc comments and removing @ConstructorDoc
 */
public class ConstructorParser extends VoidVisitorAdapter<Void> {

    // logger
    private final Log log = LogFactory.getLog(this.getClass());

    /**
     * sets java doc comments generated from unidoc @ConstructorDoc annotation.
     * Also removes @ConstructorDoc annotations from source code
     *
     * @param cd constructor to be accessed
     * @param arg void
     */
    public void visit(ConstructorDeclaration cd, Void arg) {
        super.visit(cd, arg);
        if (cd.isAnnotationPresent(ConstructorDoc.class)) {
            ConstructorDocumentation constructorDocumentation = new ConstructorDocumentation(cd);
            cd.setJavadocComment(constructorDocumentation.getJavadoc());
            cd.getAnnotationByClass(ConstructorDoc.class).ifPresent(Node::remove);
        } else {
            log.info(cd.getNameAsString() + "() is not annotated with @" + ConstructorDoc.class.getSimpleName());
        }
    }
}
