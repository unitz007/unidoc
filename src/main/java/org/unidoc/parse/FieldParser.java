package org.unidoc.parse;

import com.github.javaparser.Position;
import com.github.javaparser.Range;
import com.github.javaparser.TokenRange;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.javadoc.Javadoc;
import com.github.javaparser.javadoc.JavadocBlockTag;
import com.github.javaparser.javadoc.description.JavadocDescription;
import org.unidoc.ClassDoc;
import org.unidoc.FieldDoc;
import org.unidoc.MethodDoc;

@ClassDoc(version = "1.0.0",
        author = "<a href=mailto:dinneyacharles5@gmail.com>Dinneya Charles</a>",
        value = "class to parse variables."
)
public class FieldParser extends VoidVisitorAdapter<Void> {

    @MethodDoc("Overridden method")
    @Override
    public void visit(FieldDeclaration fd, Void arg) {
        super.visit(fd, arg);
        if (fd.isAnnotationPresent(FieldDoc.class)) {
           fd.setJavadocComment(new JavadocComment("hbddsuidss"));
        }
    }
}
