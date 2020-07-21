package org.unidoc.parse;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.unidoc.ClassDoc;

public class ClassParser extends VoidVisitorAdapter<Void> {

    @Override
    public void visit(ClassOrInterfaceDeclaration cd, Void arg) {
        super.visit(cd, arg);
        cd.getAnnotationByClass(ClassDoc.class).ifPresent(annotationExpr -> {
            System.out.println(cd.getNameAsString());
        });
    }
}
