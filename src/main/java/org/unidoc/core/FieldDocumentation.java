package org.unidoc.core;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.javadoc.Javadoc;
import com.github.javaparser.javadoc.description.JavadocDescription;
import org.unidoc.FieldDoc;
import org.unidoc.utils.Utilities;

import java.util.Optional;

public class FieldDocumentation {

    private NodeList<MemberValuePair> pairs;

    public FieldDocumentation(FieldDeclaration fd) {
        Optional<AnnotationExpr> expr = fd.getAnnotationByClass(FieldDoc.class);
        if (expr.isPresent()) {
           expr.ifPresent(annotation -> {
                //if (annotation.isNormalAnnotationExpr()) {
                    pairs = annotation.asNormalAnnotationExpr().getPairs();
                //}
            });
        }
    }

    private JavadocDescription description() {
        JavadocDescription description = null;
            for (MemberValuePair pair: pairs) {
                description = JavadocDescription
                        .parseText(Utilities.replace(pair.getValue().toString()));
            }

        return description;
    }

    public Javadoc javadoc() {
        return new Javadoc(description());
    }

}
