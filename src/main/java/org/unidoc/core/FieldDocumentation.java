package org.unidoc.core;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.javadoc.Javadoc;
import com.github.javaparser.javadoc.description.JavadocDescription;
import org.unidoc.annotations.FieldDoc;
import org.unidoc.blocktagSetter.JavadocBlocktagSetter;

import java.util.Optional;

public class FieldDocumentation {

    private Javadoc javadoc;
    private NodeList<MemberValuePair> pairs;
    JavadocBlocktagSetter javadocBlocktagSetter = new JavadocBlocktagSetter();

    /**
     * constructor with one parameter.
     * @param fd field declaration
     */
    public FieldDocumentation(FieldDeclaration fd) {
        Optional<AnnotationExpr> expr = fd.getAnnotationByClass(FieldDoc.class);
        if (expr.isPresent()) {
           expr.ifPresent(annotation -> {
                //if (annotation.isNormalAnnotationExpr()) {
                    pairs = annotation.asNormalAnnotationExpr().getPairs();
                //}
            });
        }
    }

    /**
     * creates and return an instance of JavadocDescription.
     * @return an instance of JavadocDescription
     */
    private JavadocDescription description() {
        return javadocBlocktagSetter.setDescription(pairs);
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
     * sets javadoc @serial tag
     */
    private void serialTag() {
        javadocBlocktagSetter.setSerialTag(javadoc, pairs);
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
     * sets javadoc @deprecated tag
     */
    public void deprecatedTag() {
        javadocBlocktagSetter.setDeprecatedTag(javadoc, pairs);
    }

    public Javadoc javadoc() {
        javadoc = new Javadoc(description());
        seeTag();
        sinceTag();
        serialTag();
        serialFieldTag();
        deprecatedTag();
        return javadoc;
    }

}
