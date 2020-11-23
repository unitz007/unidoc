package org.unidoc.parse;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.unidoc.annotations.ClassDoc;
import org.unidoc.core.ClassDocumentation;

public class ClassParser extends VoidVisitorAdapter<Void> {

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
        ClassDocumentation classDocumentation = new ClassDocumentation(cd);
        cd.setJavadocComment(classDocumentation.getJavadoc());
        cd.getAnnotationByClass(ClassDoc.class).ifPresent(Node::remove);
    }
}
