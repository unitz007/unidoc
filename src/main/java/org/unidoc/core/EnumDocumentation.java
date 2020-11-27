package org.unidoc.core;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.modules.ModuleDeclaration;
import com.github.javaparser.javadoc.Javadoc;
import com.github.javaparser.javadoc.description.JavadocDescription;
import org.unidoc.annotations.EnumDoc;
import org.unidoc.annotations.ModuleDoc;
import org.unidoc.blocktagSetter.JavadocBlocktagSetter;

import java.util.Optional;

/**
 * has methods for transforming @EnumDoc annotations to java doc comments
 */
public class EnumDocumentation {

    private Javadoc javadoc;
    private NodeList<MemberValuePair> pairs;
    JavadocBlocktagSetter javadocBlocktagSetter = new JavadocBlocktagSetter();
    private EnumDeclaration ed;
    private AnnotationExpr annotationExpr;

    /**
     * assigns value to pairs
     * @param ed enum declaration
     */
    public EnumDocumentation(EnumDeclaration ed) {
        this.ed = ed;
        Optional<AnnotationExpr> expr = ed.getAnnotationByClass(EnumDoc.class);
        if (expr.isPresent()) {
            expr.ifPresent(annotation -> {
                pairs = annotation.asNormalAnnotationExpr().getPairs();
                this.annotationExpr = annotation;
            });
        }
    }

    /**
     * sets description of enum
     * @return javadoc description
     */
    private JavadocDescription description() {
        return javadocBlocktagSetter.setDescription(pairs);
    }

    /**
     *
     * sets javadoc @author tag
     */
    public void authorTag() {
        javadocBlocktagSetter.setAuthorTag(javadoc, pairs);
    }

    /**
     *
     * sets javadoc @version tag
     */
    public void versionTag() {
        javadocBlocktagSetter.setVersionTag(javadoc, pairs);
    }

    /**
     *
     * sets javadoc @see tag
     */
    private void seeTag() {
        javadocBlocktagSetter.setSeeTag(javadoc, pairs);
    }

    /**
     *
     * sets javadoc @since tag
     */
    private void sinceTag() {
        javadocBlocktagSetter.setSinceTag(javadoc, pairs);
    }

    /**
     *
     * sets javadoc @serialField tag
     */
    private void serialFieldTag() {
        javadocBlocktagSetter.setSerialFieldTag(javadoc, pairs);
    }

    /**
     *
     * sets javadoc @hidden tag
     */
    private void hiddenTag() {
        javadocBlocktagSetter.setHiddenTag(javadoc, pairs);
    }

    /**
     *
     * sets javadoc @deprecated tag
     */
    public void deprecatedTag() {
        javadocBlocktagSetter.setDeprecatedTag(javadoc, pairs);
    }

    /**
     * calls methods for setting javadoc comments.
     * @return javadoc
     */
    public Javadoc getJavadoc() {
        javadoc = new Javadoc(description());
        authorTag();
        versionTag();
        seeTag();
        sinceTag();
        serialFieldTag();
        hiddenTag();
        deprecatedTag();
        return javadoc;
    }
}
