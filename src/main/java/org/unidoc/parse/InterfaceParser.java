package org.unidoc.parse;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.unidoc.annotations.InterfaceDoc;
import org.unidoc.core.InterfaceDocumentation;

import java.util.Optional;

/**
 *
 * has method for setting java doc comments and removing @InterfaceDoc
 */
public class InterfaceParser extends VoidVisitorAdapter<Void> {

    private final Log log = LogFactory.getLog(this.getClass());

    /**
     * sets java doc comments generated from unidoc @InterfaceDoc annotation.
     * Also removes @InterfaceDoc annotations from source code
     *
     * @param id interface to be assessed
     * @param arg void
     */
    @Override
    public void visit(ClassOrInterfaceDeclaration id, Void arg) {
        super.visit(id, arg);
        Optional<AnnotationExpr> expr = id.getAnnotationByClass(InterfaceDoc.class);
        if (expr.isPresent()) {
            InterfaceDocumentation interfaceDocumentation = new InterfaceDocumentation(id);
            id.setJavadocComment(interfaceDocumentation.getJavadoc());
            expr.get().remove();
        } else {
            log.info("Interface: " + id.getNameAsString() + ", is not annotated with @" + InterfaceDoc.class.getSimpleName());
        }
    }
}
