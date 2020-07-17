package org.unidoc.parse;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.javadoc.Javadoc;
import com.github.javaparser.javadoc.JavadocBlockTag;
import com.github.javaparser.javadoc.description.JavadocDescription;
import org.unidoc.MethodDoc;

public class MethodParser extends VoidVisitorAdapter<Void> {

    @Override
    public void visit(MethodDeclaration md, Void arg) {
        super.visit(md, arg);
        if (md.isAnnotationPresent(MethodDoc.class)) {
            Javadoc javadoc = new Javadoc(JavadocDescription.parseText("java doc"));
            javadoc.addBlockTag(JavadocBlockTag.Type.RETURN.name().toLowerCase(), "returns bad guy");
            javadoc.addBlockTag(JavadocBlockTag.Type.AUTHOR.name().toLowerCase(), "Dinneya Charles");
            javadoc.addBlockTag(JavadocBlockTag.Type.EXCEPTION.name().toLowerCase(), " throws exception");
            md.setJavadocComment(javadoc);
        }
    }
}
