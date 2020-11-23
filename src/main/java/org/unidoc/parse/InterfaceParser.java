package org.unidoc.parse;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.unidoc.annotations.InterfaceDoc;
import org.unidoc.core.ClassDocumentation;
import org.unidoc.core.InterfaceDocumentation;

public class InterfaceParser extends VoidVisitorAdapter<Void> {

    /**
     * converts unidoc interface annotations to java doc comments.
     * Also removes @InterfaceDoc annotations from source code
     *
     * @param id interface(s) to be accessed
     * @param arg void
     */
    @Override
    public void visit(ClassOrInterfaceDeclaration id, Void arg) {
        super.visit(id, arg);
        InterfaceDocumentation interfaceDocumentation = new InterfaceDocumentation(id);
        id.setJavadocComment(interfaceDocumentation.getJavadoc());
        id.getAnnotationByClass(InterfaceDoc.class).ifPresent(Node::remove);
    }
}
