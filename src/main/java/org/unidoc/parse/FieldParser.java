package org.unidoc.parse;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.unidoc.annotations.ClassDoc;
import org.unidoc.annotations.FieldDoc;
import org.unidoc.annotations.MethodDoc;
import org.unidoc.core.FieldDocumentation;

import java.util.List;
import java.util.Optional;


public class FieldParser extends VoidVisitorAdapter<Void> {

    private final Log log = LogFactory.getLog(this.getClass());

    /**
     * Converts unidoc field annotations to java doc comments.
     * Also removes @FieldDoc annotations from source code
     *
     * @param fd field(s) to be accessed
     * @param arg void
     */
    @Override
    public void visit(FieldDeclaration fd, Void arg) {
        super.visit(fd, arg);
        Optional<AnnotationExpr> expr = fd.getAnnotationByClass(FieldDoc.class);
        if (expr.isPresent()) {
            FieldDocumentation fieldDocumentation = new FieldDocumentation(fd);
            fd.setJavadocComment(fieldDocumentation.javadoc());
            expr.get().remove();
        } else {
            fd.getVariables().forEach(variableDeclarator -> {
                log.info("Variable: " + variableDeclarator.getNameAsString()
                        + ", is not annotated with @" + FieldDoc.class.getSimpleName());
            });
        }
    }
}
