package org.unidoc.core;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.javadoc.Javadoc;
import com.github.javaparser.javadoc.description.JavadocDescription;
import org.unidoc.annotations.MethodDoc;
import org.unidoc.blocktagSetter.JavadocBlocktagSetter;
import org.unidoc.utils.Utilities;

/**
 * has methods for transforming @MethodDoc annotations to java doc comments
 */
public class MethodDocumentation {

    private Javadoc javadoc;
    private NodeList<MemberValuePair> pairs;
    private final MethodDeclaration md;
    private AnnotationExpr annotationExpr;
    private JavadocBlocktagSetter javadocBlocktagSetter = new JavadocBlocktagSetter();

    /**
     * assigns value to pairs and annotationExpr
     * @param md method declaration
     */
    public MethodDocumentation(MethodDeclaration md) {
        this.md = md;
        // checks if method has @MethodDoc annotation
        md.getAnnotationByClass(MethodDoc.class).ifPresent(annotation -> {
                if (annotation.isNormalAnnotationExpr()) {
                    this.pairs = annotation.asNormalAnnotationExpr().getPairs();
                    this.annotationExpr = annotation;
                }
        });
    }

    /**
     * sets description of method
     * @return an instance of JavadocDescription
     */
    protected JavadocDescription description() {
        return javadocBlocktagSetter.setMethodDescription(md, annotationExpr, pairs);
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
        javadocBlocktagSetter.setMethodParamTag(md, annotationExpr, javadoc);
    }

    /**
     * sets @return tag
     */
    private void returnTag() {
        javadocBlocktagSetter.setMethodReturnTag(md, javadoc, pairs);
    }

    /**
     * sets javadoc @throws tag.
     */
    private void throwTag() {
        javadocBlocktagSetter.setMethodThrowTag(md, javadoc, pairs);
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
     * sets @serialData tag
     */
    private void serialDataTag() {
        javadocBlocktagSetter.setSerialDataTag(javadoc, pairs);
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
        paramTag(); // add @param tag
        returnTag(); // add @return tag
        throwTag(); // add @throws tag
        seeTag();
        sinceTag();
        serialDataTag();
        hiddenTag();
        deprecatedTag();
        return javadoc;
    }
}
