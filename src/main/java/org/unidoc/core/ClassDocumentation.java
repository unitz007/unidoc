package org.unidoc.core;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.javadoc.Javadoc;
import com.github.javaparser.javadoc.description.JavadocDescription;
import org.unidoc.annotations.ClassDoc;
import org.unidoc.blocktagSetter.JavadocBlocktagSetter;

import java.util.Optional;

public class ClassDocumentation {

    private NodeList<MemberValuePair> pairs;
    private Javadoc javadoc;
    private JavadocBlocktagSetter javadocBlocktagSetter = new JavadocBlocktagSetter();

    /**
     * constructor with one parameter.
     * @param cd class declaration
     */
    public ClassDocumentation(ClassOrInterfaceDeclaration cd) {
        Optional<AnnotationExpr> annotationByClass = cd.getAnnotationByClass(ClassDoc.class);
        annotationByClass.ifPresent(annotationExpr -> {
            this.pairs = annotationExpr.asNormalAnnotationExpr().getPairs();
        });
    }

    /**
     * creates and return an instance of JavadocDescription.
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
//        String authorTag = Utilities.lowerCaseBlockTag("AUTHOR");
//
//        pairs.stream()
//                .filter(memberValuePair -> memberValuePair.getNameAsString().equals("author"))
//                .forEach(memberValuePair -> {
//                        NodeList<Expression> values = memberValuePair.getValue()
//                                                        .asArrayInitializerExpr().getValues();
//                        values.forEach(val -> javadoc.addBlockTag(authorTag, Utilities.replace(val.toString())));
//                        });
    }

    /**
     *
     * sets javadoc @version tag
     */
    public void versionTag() {
        javadocBlocktagSetter.setVersionTag(javadoc, pairs);

//        String versionTag = Utilities.lowerCaseBlockTag("VERSION");
//
//        pairs.stream()
//                .filter(memberValuePair -> memberValuePair.getNameAsString().equals("version"))
//                .forEach(memberValuePair -> {
//                        String value = memberValuePair.getValue().toString();
//                        javadoc.addBlockTag(versionTag, Utilities.replace(value)); });
    }

    /**
     *
     * sets javadoc @see tag
     */
    public void seeTag() {
        javadocBlocktagSetter.setSeeTag(javadoc, pairs);
//        String seeTag = Utilities.lowerCaseBlockTag("SEE");
//
//        pairs.stream()
//                .filter(memberValuePair -> memberValuePair.getNameAsString().equals("see"))
//                .forEach(memberValuePair -> {
//                        String value = memberValuePair.getValue().toString();
//                        javadoc.addBlockTag(seeTag, Utilities.replace(value)); });
    }

    /**
     *
     * sets javadoc @since tag
     */
    public void sinceTag() {
        javadocBlocktagSetter.setSinceTag(javadoc, pairs);
//        String sinceTag = Utilities.lowerCaseBlockTag("SINCE");
//
//        pairs.stream()
//                .filter(memberValuePair -> memberValuePair.getNameAsString().equals("since"))
//                .forEach(memberValuePair -> {
//                        String value = memberValuePair.getValue().toString();
//                        javadoc.addBlockTag(sinceTag, Utilities.replace(value)); });
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
     * sets javadoc @deprecated tag
     */
    private void deprecatedTag() {
        javadocBlocktagSetter.setDeprecatedTag(javadoc, pairs);
    }

    /**
     * defines and sets javadoc.
     * @return javadoc
     */
    public Javadoc getJavadoc() {
        javadoc = new Javadoc(description());
        authorTag();
        versionTag();
        seeTag();
        sinceTag();
        serialTag();
        deprecatedTag();
        return javadoc;
    }
}