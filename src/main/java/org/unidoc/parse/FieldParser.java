package org.unidoc.parse;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.javadoc.Javadoc;
import com.github.javaparser.javadoc.description.JavadocDescription;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.unidoc.ClassDoc;
import org.unidoc.FieldDoc;
import org.unidoc.MethodDoc;
import org.unidoc.core.FieldDocumentation;

import java.util.Optional;

@ClassDoc(version = "1.0.0",
        author = "<a href=mailto:dinneyacharles5@gmail.com>Dinneya Charles</a>",
        value = "class to parse variables."
)
public class FieldParser extends VoidVisitorAdapter<Void> {

    private final Log log = LogFactory.getLog(this.getClass());

    @MethodDoc(description = "Overridden method")
    @Override
    public void visit(FieldDeclaration fd, Void arg) {
        super.visit(fd, arg);
        Optional<AnnotationExpr> expr = fd.getAnnotationByClass(FieldDoc.class);
        if (expr.isPresent()) {
            FieldDocumentation fieldDocumentation = new FieldDocumentation(fd);
            fd.setJavadocComment(fieldDocumentation.javadoc());
            System.out.println(fd);
        } else {
            fd.getVariables().forEach(variableDeclarator -> {
                log.info("Variable: " + variableDeclarator.getNameAsString()
                        + ", does not annotated with @" + FieldDoc.class.getSimpleName());
            });
        }
    }
}
