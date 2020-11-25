package org.unidoc.core;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.AnnotationMemberDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.javadoc.Javadoc;
import com.github.javaparser.javadoc.description.JavadocDescription;
import org.unidoc.annotations.AnnotationMemberDoc;
import org.unidoc.annotations.ConstructorDoc;
import org.unidoc.blocktagSetter.JavadocBlocktagSetter;

/**
 * has methods for transforming @AnnotationMemberDoc annotations to java doc comments
 */
public class AnnotationMemberDocumentation {

    private JavadocBlocktagSetter javadocBlocktagSetter = new JavadocBlocktagSetter();
    private Javadoc javadoc;
    private NodeList<MemberValuePair> pairs;
    private AnnotationMemberDeclaration aed;

    /**
     * assigns value to pairs and annotationExpr
     * @param aed annotationMember declaration
     */
    public AnnotationMemberDocumentation(AnnotationMemberDeclaration aed) {

        this.aed = aed;

        aed.getAnnotationByClass(AnnotationMemberDoc.class).ifPresent(annotation -> {
            if (annotation.isNormalAnnotationExpr()) {
                this.pairs = annotation.asNormalAnnotationExpr().getPairs();
            }
        });
    }

    /**
     * sets description of annotationMember
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
     * sets @return tag
     */
    private void returnTag() {
        javadocBlocktagSetter.setAnnotationMemberReturnTag(aed, javadoc, pairs);
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
        javadoc = new Javadoc(description()); // instantiates javadoc.
        versionTag();
        returnTag(); // add @return tag
        seeTag();
        sinceTag();
        hiddenTag();
        deprecatedTag();
        return javadoc;
    }


}
