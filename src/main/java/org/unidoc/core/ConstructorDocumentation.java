package org.unidoc.core;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.javadoc.Javadoc;
import com.github.javaparser.javadoc.description.JavadocDescription;
import org.unidoc.annotations.ConstructorDoc;
import org.unidoc.blocktagSetter.JavadocBlocktagSetter;

public class ConstructorDocumentation {

    private JavadocBlocktagSetter javadocBlocktagSetter = new JavadocBlocktagSetter();
    private Javadoc javadoc;
    private NodeList<MemberValuePair> pairs;
    private final ConstructorDeclaration cd;
    private AnnotationExpr annotationExpr;

    /**
     * constructor with one parameter.
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
     * @return an instance of JavadocDescription
     */
    protected JavadocDescription description() {
        return javadocBlocktagSetter.setDescription(pairs);
//        AtomicReference<JavadocDescription> description = new AtomicReference<>();
//        pairs.stream()
//                .filter( memberValuePair -> memberValuePair.getNameAsString().equals("description"))
//                .forEach( memberValuePair -> {
//                    String value = memberValuePair.getValue().toString()
//                                            .replace(" + ", "\n");
//                    description.set(JavadocDescription.parseText(Utilities.replace(value + "."))); });
//        return description.get();
    }

    /**
     * defines and set javadoc @param tag.
     */
    private void paramTag() {
        javadocBlocktagSetter.setConstructorParamTag(cd, annotationExpr, javadoc);
    }

    /**
     * defines and sets @return tag
     */
    private void returnTag() {
        javadocBlocktagSetter.setConstructorReturnTag(cd, javadoc, pairs);
    }

    /**
     * defines and set javadoc @throws tag.
     */
    private void throwTag() {
        javadocBlocktagSetter.setConstructorThrowTag(cd, javadoc, pairs);
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
     * defines and set javadoc.
     * @return Javadoc
     */
    public Javadoc getJavadoc() {
        javadoc = new Javadoc(description());
        paramTag();
        returnTag();
        throwTag();
        seeTag();
        sinceTag();
        serialDataTag();
        deprecatedTag();
        return javadoc;
    }
}
