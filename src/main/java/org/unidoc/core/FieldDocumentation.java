package org.unidoc.core;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.javadoc.Javadoc;
import com.github.javaparser.javadoc.description.JavadocDescription;
import org.unidoc.FieldDoc;
import org.unidoc.MethodDoc;
import org.unidoc.utils.Utilities;

public class FieldDocumentation {

    private final FieldDeclaration fd;
    private NodeList<MemberValuePair> pairs;

    public FieldDocumentation(FieldDeclaration fd) {
        this.fd = fd;
        fd.getAnnotationByClass(FieldDoc.class).ifPresent(annotation -> {
            if (annotation.isNormalAnnotationExpr()) {
                pairs = annotation.asNormalAnnotationExpr().getPairs();
            }
        });
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
