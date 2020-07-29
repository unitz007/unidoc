package org.unidoc.core;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.javadoc.Javadoc;
import com.github.javaparser.javadoc.description.JavadocDescription;
import org.unidoc.ClassDoc;
import org.unidoc.utils.Utilities;

import java.util.Optional;

public class ClassDocumentation {

    private NodeList<MemberValuePair> pairs;
    private Javadoc javadoc;

    /**
     *
     * @param cd class declaration
     */
    public ClassDocumentation(ClassOrInterfaceDeclaration cd) {
        Optional<AnnotationExpr> annotationByClass = cd.getAnnotationByClass(ClassDoc.class);
        annotationByClass.ifPresent(annotationExpr -> {
            this.pairs = annotationExpr.asNormalAnnotationExpr().getPairs();
        });
    }

    private void author() {
        String authorTag = Utilities.lowerCaseBlockTag("AUTHOR");
        pairs.forEach(memberValuePair -> {
            if (memberValuePair.getNameAsString().equals("author")) {
                if (!memberValuePair.getValue().isArrayInitializerExpr()) {
                    javadoc.addBlockTag(authorTag, Utilities.replace(memberValuePair.getValue().toString()));
                } else {
                    NodeList<Expression> values = memberValuePair.getValue().asArrayInitializerExpr().getValues();
                    values.forEach(val -> {
                        javadoc.addBlockTag(authorTag, Utilities.replace(val.toString()));
                    });

                }
            }
        });
    }

    private JavadocDescription description() {
        JavadocDescription description = null;
        for (MemberValuePair memberValuePair : pairs) {
            if (memberValuePair.getNameAsString().equals("description")) {
                description = JavadocDescription.parseText(Utilities.replace(memberValuePair.getValue().toString() + "."));
            }
        }
        return description;
    }

    /**
     *
     * @return javadoc
     */
    public Javadoc getJavadoc() {
        javadoc = new Javadoc(description());
        author();
        return javadoc;
    }

}
