package org.unidoc.core;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.javadoc.Javadoc;
import com.github.javaparser.javadoc.description.JavadocDescription;
import org.unidoc.annotations.ConstructorDoc;
import org.unidoc.blocktagSetter.JavadocBlocktagSetter;

/**
 * has methods for transforming @ConstructorDoc annotations to java doc comments
 */
public class ConstructorDocumentation {

    private JavadocBlocktagSetter javadocBlocktagSetter = new JavadocBlocktagSetter();
    private Javadoc javadoc;
    private NodeList<MemberValuePair> pairs;
    private final ConstructorDeclaration cd;
    private AnnotationExpr annotationExpr;

    /**
     * assigns value to pairs and annotationExpr
     * @param cd constructor declaration
     */
    public ConstructorDocumentation(ConstructorDeclaration cd) {
        this.cd = cd;
        // checks if constructor has @ConstructorDoc annotation
        cd.getAnnotationByClass(ConstructorDoc.class).ifPresent(annotation -> {
            if (annotation.isNormalAnnotationExpr()) {
                this.pairs = annotation.asNormalAnnotationExpr().getPairs();
                this.annotationExpr = annotation;
            }

        });
    }

    /**
     * sets description of constructor
     * @return javadoc description
     */
    protected JavadocDescription description() {
        return javadocBlocktagSetter.setDescription(pairs);
    }

    /**
     *
     * sets javadoc @version tag
     */
    public void versionTag() {
        javadocBlocktagSetter.setVersionTag(javadoc, pairs);
    }

    /**
     * sets javadoc @param tag.
     */
    private void paramTag() {
        javadocBlocktagSetter.setConstructorParamTag(cd, annotationExpr, javadoc);
    }

    /**
     * sets javadoc @throws tag.
     */
    private void throwTag() {
        javadocBlocktagSetter.setConstructorThrowTag(cd, javadoc, pairs);
    }

    /**
     * sets @see tag
     */
    private void seeTag() {
        javadocBlocktagSetter.setSeeTag(javadoc, pairs);
    }

    /**
     * sets @since tag
     */
    private void sinceTag() {
        javadocBlocktagSetter.setSinceTag(javadoc, pairs);
    }

    /**
     *
     * sets javadoc @hidden tag
     */
    private void hiddenTag() {
        javadocBlocktagSetter.setHiddenTag(javadoc, pairs);
    }

    /**
     * sets @deprecated tag
     */
    private void deprecatedTag() {
        javadocBlocktagSetter.setDeprecatedTag(javadoc, pairs);
    }

    /**
     * calls methods for setting javadoc comments.
     * @return javadoc
     */
    public Javadoc getJavadoc() {
        javadoc = new Javadoc(description());
        versionTag();
        paramTag();
        throwTag();
        seeTag();
        sinceTag();
        hiddenTag();
        deprecatedTag();
        return javadoc;
    }
}
