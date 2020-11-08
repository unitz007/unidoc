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

public class MethodDocumentation {

    private Javadoc javadoc;
    private NodeList<MemberValuePair> pairs;
    private final MethodDeclaration md;
    private AnnotationExpr annotationExpr;
    private JavadocBlocktagSetter javadocBlocktagSetter = new JavadocBlocktagSetter();

    /**
     * constructor with one parameter.
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
     * creates and return an instance of JavadocDescription.
     * @return an instance of JavadocDescription
     */
    protected JavadocDescription description() {
        return javadocBlocktagSetter.setMethodDescription(md, annotationExpr, pairs);
    }

    /**
     * defines and sets javadoc @param tag.
     */
    private void paramTag() {
        javadocBlocktagSetter.setMethodParamTag(md, annotationExpr, javadoc);
    }

    /**
     * defines and sets @return tag
     */
    private void returnTag() {
        javadocBlocktagSetter.setMethodReturnTag(md, javadoc, pairs);
    }

    /**
     * defines and sets javadoc @throws tag.
     */
    private void throwTag() {
        javadocBlocktagSetter.setMethodThrowTag(md, javadoc, pairs);
    }

    /**
     * defines and sets @see tag
     */
    private void seeTag() {
        javadocBlocktagSetter.setSeeTag(javadoc, pairs);
    }

    /**
     * defines and sets @since tag
     */
    private void sinceTag() {
        javadocBlocktagSetter.setSinceTag(javadoc, pairs);
    }

    /**
     * defines and sets @serialData tag
     */
    private void serialDataTag() {
        javadocBlocktagSetter.setSerialDataTag(javadoc, pairs);
    }

    /**
     * defines and sets @deprecated tag
     */
    private void deprecatedTag() {
        javadocBlocktagSetter.setDeprecatedTag(javadoc, pairs);
    }

    /**
     * defines and sets javadoc.
     * @return Javadoc
     */
    public Javadoc getJavadoc() {
        javadoc = new Javadoc(description()); // instantiates javadoc.
        paramTag(); // add @param tag
        returnTag(); // add @return tag
        throwTag(); // add @throws tag
        seeTag();
        sinceTag();
        serialDataTag();
        deprecatedTag();
        return javadoc;
    }
}
