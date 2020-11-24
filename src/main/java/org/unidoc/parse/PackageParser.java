package org.unidoc.parse;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.nodeTypes.NodeWithJavadoc;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.base.Strings;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.unidoc.annotations.PackageDoc;
import org.unidoc.core.PackageDocumentation;

import java.util.List;
import java.util.Optional;

public class PackageParser extends VoidVisitorAdapter<Void> {

    private final Log log = LogFactory.getLog(this.getClass());

    /**
     * Converts unidoc package annotations to java doc comments.
     * Also removes @PackageDoc annotations from source code
     *
     * @param pd package(s) to be accessed
     * @param arg void
     */
    @Override
    public void visit(PackageDeclaration pd, Void arg) {
        super.visit(pd, arg);
        PackageDocumentation packageDocumentation = new PackageDocumentation(pd);
        if(pd.isAnnotationPresent(PackageDoc.class)) {
            pd.setComment(packageDocumentation.getJavadoc());
            pd.getAnnotationByClass(PackageDoc.class).ifPresent(Node::remove);
        } else {
            log.info(pd.getNameAsString() + " is not annotated with @" + PackageDoc.class.getSimpleName());
        }
    }
}
