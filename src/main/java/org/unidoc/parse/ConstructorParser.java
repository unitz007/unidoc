package org.unidoc.parse;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.unidoc.annotations.ConstructorDoc;
import org.unidoc.core.ConstructorDocumentation;

public class ConstructorParser extends VoidVisitorAdapter<Void> {

    private final Log log = LogFactory.getLog(this.getClass()); // logger

    /**
     * parses constructor.
     * @param cd Method to be accessed
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
