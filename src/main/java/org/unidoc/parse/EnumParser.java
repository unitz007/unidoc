package org.unidoc.parse;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.modules.ModuleDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.unidoc.annotations.EnumDoc;
import org.unidoc.annotations.ModuleDoc;
import org.unidoc.core.EnumDocumentation;
import org.unidoc.core.ModuleDocumentation;

public class EnumParser extends VoidVisitorAdapter<Void> {

    private final Log log = LogFactory.getLog(this.getClass());

    /**
     * Converts unidoc enum annotations to javadoc comments.
     * Also removes @EnumDoc annotations from source code
     *
     * @param ed enum to be assessed
     * @param arg void
     */
    @Override
    public void visit(EnumDeclaration ed, Void arg) {
        super.visit(ed, arg);

        if(ed.isAnnotationPresent(EnumDoc.class)) {
            EnumDocumentation enumDocumentation = new EnumDocumentation(ed);
            ed.setJavadocComment(enumDocumentation.getJavadoc());
            ed.getAnnotationByClass(EnumDoc.class).ifPresent(Node::remove);
        } else {
            log.info(ed.getNameAsString() + " is not annotated with @" + EnumDoc.class.getSimpleName());
        }
    }
}
