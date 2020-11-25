package org.unidoc.parse;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.modules.ModuleDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.unidoc.annotations.ModuleDoc;
import org.unidoc.annotations.PackageDoc;
import org.unidoc.core.ModuleDocumentation;
import org.unidoc.core.PackageDocumentation;

public class ModuleParser extends VoidVisitorAdapter<Void> {

    private final Log log = LogFactory.getLog(this.getClass());

    /**
     * Converts unidoc module annotations to javadoc comments.
     * Also removes @ModuleDoc annotations from source code
     *
     * @param mdd module to be assessed
     * @param arg void
     */
    @Override
    public void visit(ModuleDeclaration mdd, Void arg) {
        super.visit(mdd, arg);

        if(mdd.isAnnotationPresent(ModuleDoc.class)) {
            ModuleDocumentation moduleDocumentation = new ModuleDocumentation(mdd);
            mdd.setComment(moduleDocumentation.getJavadoc());
            mdd.getAnnotationByClass(ModuleDoc.class).ifPresent(Node::remove);
        } else {
            log.info(mdd.getNameAsString() + " is not annotated with @" + ModuleDoc.class.getSimpleName());
        }
    }
}
