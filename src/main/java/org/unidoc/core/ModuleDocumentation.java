package org.unidoc.core;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.modules.ModuleDeclaration;
import com.github.javaparser.javadoc.Javadoc;
import com.github.javaparser.javadoc.description.JavadocDescription;
import org.unidoc.annotations.ModuleDoc;
import org.unidoc.blocktagSetter.JavadocBlocktagSetter;
import java.util.Optional;

/**
 * calls and passes required parameters to methods that transform @ModuleDoc annotations to java doc comments
 */
public class ModuleDocumentation {

    private Javadoc javadoc;

    private NodeList<MemberValuePair> pairs;

    JavadocBlocktagSetter javadocBlocktagSetter = new JavadocBlocktagSetter();

    /**
     * assigns value to pairs
     * @param mdd module declaration
     */
    public ModuleDocumentation(ModuleDeclaration mdd) {
        Optional<AnnotationExpr> expr = mdd.getAnnotationByClass(ModuleDoc.class);
        if (expr.isPresent()) {
            expr.ifPresent(annotation -> {
                pairs = annotation.asNormalAnnotationExpr().getPairs();
            });
        }
    }

    /**
     * sets description of module
     * @return javadoc description
     */
    private JavadocDescription description() {
        return javadocBlocktagSetter.setDescription(pairs);
    }

    /**
     * sets javadoc @author tag
     */
    public void authorTag() {
        javadocBlocktagSetter.setAuthorTag(javadoc, pairs);
    }

    /**
     * sets javadoc @version tag
     */
    public void versionTag() {
        javadocBlocktagSetter.setVersionTag(javadoc, pairs);
    }

    /**
     * sets javadoc @see tag
     */
    private void seeTag() {
        javadocBlocktagSetter.setSeeTag(javadoc, pairs);
    }

    /**
     * sets javadoc @since tag
     */
    private void sinceTag() {
        javadocBlocktagSetter.setSinceTag(javadoc, pairs);
    }

    /**
     * sets javadoc @serialField tag
     */
    private void serialFieldTag() {
        javadocBlocktagSetter.setSerialFieldTag(javadoc, pairs);
    }

    /**
     * sets javadoc @provides tag
     */
    public void providesTag() {
        javadocBlocktagSetter.setProvidesTag(javadoc, pairs);
    }

    /**
     * sets javadoc @uses tag
     */
    public void usesTag() {
        javadocBlocktagSetter.setUsesTag(javadoc, pairs);
    }

    /**
     * sets javadoc @deprecated tag
     */
    public void deprecatedTag() {
        javadocBlocktagSetter.setDeprecatedTag(javadoc, pairs);
    }

    /**
     * calls methods for setting javadoc comments.
     * @return javadocComment
     */
    public JavadocComment getJavadoc() {
        javadoc = new Javadoc(description());
        authorTag();
        versionTag();
        seeTag();
        sinceTag();
        serialFieldTag();
        providesTag();
        usesTag();
        deprecatedTag();
        return javadoc.toComment().asJavadocComment();
    }
}
