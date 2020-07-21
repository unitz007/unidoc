package org.unidoc.core;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.javadoc.Javadoc;
import org.unidoc.ClassDoc;

import java.util.Optional;

public class ClassDocumentation {

    private NodeList<MemberValuePair> pairs;
    private Javadoc javadoc;

    public ClassDocumentation(ClassOrInterfaceDeclaration cd) {
        Optional<AnnotationExpr> annotationByClass = cd.getAnnotationByClass(ClassDoc.class);
        annotationByClass.ifPresent(annotationExpr -> {
            this.pairs = annotationExpr.asNormalAnnotationExpr().getPairs();
        });
    }

    public Javadoc getJavadoc() {
        return javadoc;
    }

}
