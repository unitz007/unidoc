package org.unidoc.parse;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.unidoc.annotations.ClassDoc;
import org.unidoc.annotations.InterfaceDoc;
import org.unidoc.core.ClassDocumentation;
import org.unidoc.core.InterfaceDocumentation;

import java.util.Optional;

public class ClassParser extends VoidVisitorAdapter<Void> {

    private final Log log = LogFactory.getLog(this.getClass());

    /**
     * converts unidoc class annotations to java doc comments.
     * Also removes @ClassDoc annotations from source code
     *
     * @param cd class(es) to be accessed
     * @param arg void
     */
    @Override
    public void visit(ClassOrInterfaceDeclaration cd, Void arg) {
        super.visit(cd, arg);
        Optional<AnnotationExpr> expr = cd.getAnnotationByClass(ClassDoc.class);
        if (expr.isPresent()) {
            ClassDocumentation classDocumentation = new ClassDocumentation(cd);
            cd.setJavadocComment(classDocumentation.getJavadoc());
            expr.get().remove();
        } else {
            log.info("Class: " + cd.getNameAsString()
                    + ", is not annotated with @" + ClassDoc.class.getSimpleName());
        }
    }
}
