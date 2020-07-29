package org.unidoc.parse;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.unidoc.ClassDoc;
import org.unidoc.core.ClassDocumentation;

public class ClassParser extends VoidVisitorAdapter<Void> {

    @Override
    public void visit(ClassOrInterfaceDeclaration cd, Void arg) {
        super.visit(cd, arg);
        ClassDocumentation classDocumentation = new ClassDocumentation(cd);
        cd.setJavadocComment(classDocumentation.getJavadoc());
        cd.getAnnotationByClass(ClassDoc.class).ifPresent(Node::remove);
    }
}
