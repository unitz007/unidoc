package org.unidoc.blocktagSetter;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.javadoc.Javadoc;
import com.github.javaparser.javadoc.description.JavadocDescription;
import org.unidoc.annotations.ParamDoc;
import org.unidoc.utils.Utilities;

import java.util.concurrent.atomic.AtomicReference;

public class JavadocBlocktagSetter {

    /**
     * creates and return an instance of JavadocDescription.
     * @return javadoc description
     */
    public JavadocDescription setDescription(NodeList<MemberValuePair> pairs) {
//        this.javadoc = javadoc;
//        this.pairs = pairs;

        AtomicReference<JavadocDescription> description = new AtomicReference<>();

        pairs.stream()
                .filter(memberValuePair -> memberValuePair.getNameAsString().equals("description"))
                .forEach(memberValuePair -> {
                    String value = memberValuePair.getValue().toString().replace(" + ", "");
                    String otherValue = value.replace("\\n", "\n");
                    description.set(JavadocDescription.parseText(Utilities.replace(otherValue + "."))); });
        return description.get();
    }

    /**
     * creates and return an instance of JavadocDescription.
     * @return an instance of JavadocDescription
     */
    public JavadocDescription setMethodDescription(MethodDeclaration md, AnnotationExpr annotationExpr, NodeList<MemberValuePair> pairs) {
        AtomicReference<JavadocDescription> description = new AtomicReference<>();

        if (annotationExpr.isNormalAnnotationExpr()) {
            description.set(JavadocDescription.parseText(md.getNameAsString() + "."));
            pairs.stream()
                    .filter(memberValuePair -> memberValuePair.getNameAsString().equals("description"))
                    .forEach(memberValuePair -> {
                        String value = memberValuePair.getValue().toString()
                                .replace(" + ", "\n");
                        description.updateAndGet(mdValue -> JavadocDescription.parseText(Utilities.replace(value + ".")));
                    });
        }
        return description.get();
    }

    /**
     *
     * sets javadoc @author tag
     */
    public void setAuthorTag(Javadoc javadoc, NodeList<MemberValuePair> pairs) {
//        this.javadoc = javadoc;
//        this.pairs = pairs;
        String authorTag = Utilities.lowerCaseBlockTag("AUTHOR");
        //AtomicReference<Javadoc> javadocBlockTag = new AtomicReference<>();

        pairs.stream()
                .filter(memberValuePair -> memberValuePair.getNameAsString().equals("author"))
                .forEach(memberValuePair -> {
                    NodeList<Expression> values = memberValuePair.getValue()
                            .asArrayInitializerExpr().getValues();
                    values.forEach(val -> javadoc.addBlockTag(authorTag, Utilities.replace(val.toString()))); });
    }

    /**
     *
     * sets javadoc @version tag
     */
    public void setVersionTag(Javadoc javadoc, NodeList<MemberValuePair> pairs) {
//        this.javadoc = javadoc;
//        this.pairs = pairs;

        String versionTag = Utilities.lowerCaseBlockTag("VERSION");

        pairs.stream()
                .filter(memberValuePair -> memberValuePair.getNameAsString().equals("version"))
                .forEach(memberValuePair -> {
                    String value = memberValuePair.getValue().toString();
                    javadoc.addBlockTag(versionTag, Utilities.replace(value)); });
    }

    /**
     * defines and set javadoc @param tag.
     */
    public void setConstructorParamTag(ConstructorDeclaration cd, AnnotationExpr annotationExpr, Javadoc javadoc) {

        NodeList<Parameter> parameters = cd.getParameters(); // gets constructor's parameter(s)
        String annotation = Utilities.lowerCaseBlockTag("PARAM");

        if (annotationExpr.isAnnotationExpr()) {
            for (Parameter parameter: parameters) {
                parameter.getAnnotationByClass(ParamDoc.class)
                        .ifPresent(ParamAnnotationExpr -> {
                            NodeList<MemberValuePair> paramPairs = ParamAnnotationExpr.asNormalAnnotationExpr().getPairs(); //get MemberValuePairs (description = ...)
                            paramPairs.stream()
                                    .filter(paramPair -> paramPair.getNameAsString().equals("description"))
                                    .forEach(memberValuePair -> {
                                        String value = memberValuePair.getValue().toString();
                                        javadoc.addBlockTag(annotation, parameter.getNameAsString(), Utilities.replace(value));
                                    });

                            ParamAnnotationExpr.remove();
                        });
            }
        }
    }

    public void setMethodParamTag(MethodDeclaration md, AnnotationExpr annotationExpr, Javadoc javadoc) {

        NodeList<Parameter> parameters = md.getParameters(); // gets constructor's parameter(s)
        String annotation = Utilities.lowerCaseBlockTag("PARAM");

        if (annotationExpr.isAnnotationExpr()) {
            for (Parameter parameter: parameters) {
                parameter.getAnnotationByClass(ParamDoc.class)
                        .ifPresent(ParamAnnotationExpr -> {
                            NodeList<MemberValuePair> paramPairs = ParamAnnotationExpr.asNormalAnnotationExpr().getPairs(); //get MemberValuePairs (description = ...)
                            paramPairs.stream()
                                    .filter(paramPair -> paramPair.getNameAsString().equals("description"))
                                    .forEach(memberValuePair -> {
                                        String value = memberValuePair.getValue().toString();
                                        javadoc.addBlockTag(annotation, parameter.getNameAsString(), Utilities.replace(value));
                                    });

                            ParamAnnotationExpr.remove();
                        });
            }
        }
    }

    /**
     * defines and sets @return tag
     */
    public void setConstructorReturnTag(ConstructorDeclaration cd, Javadoc javadoc, NodeList<MemberValuePair> pairs) {

        String returns = Utilities.lowerCaseBlockTag("RETURN");
        pairs.stream()
                .filter(memberValuePair -> memberValuePair.getNameAsString().equals("returns"))
                .forEach(memberValuePair -> {
                    String value = memberValuePair.getValue().toString();
                    javadoc.addBlockTag(returns, Utilities.replace(value)); });
    }


    /**
     * defines and sets @return tag
     */
    public void setMethodReturnTag(MethodDeclaration md, Javadoc javadoc, NodeList<MemberValuePair> pairs) {

        String returns = Utilities.lowerCaseBlockTag("RETURN");
        if (!md.getType().isVoidType()) {  // if method returns a value i.e NOT void.
            pairs.stream()
                    .filter(memberValuePair -> memberValuePair.getNameAsString().equals("returns"))
                    .forEach(memberValuePair -> {
                        String value = memberValuePair.getValue().toString();
                        javadoc.addBlockTag(returns, Utilities.replace(value)); });
        }
    }

    /**
     * defines and sets javadoc @throws tag.
     */
    public void setConstructorThrowTag(ConstructorDeclaration cd, Javadoc javadoc, NodeList<MemberValuePair> pairs) {
        NodeList<ReferenceType> thrownExceptions = cd.getThrownExceptions();  // gets all thrown exception(s) defined in method signature.
        String thr = Utilities.lowerCaseBlockTag("THROWS");

        pairs.stream()
                .filter(memberValuePair -> memberValuePair.getNameAsString().equals("exceptions"))
                .forEach(memberValuePair -> {
                    NodeList<Expression> exceptionValues = memberValuePair.getValue().asArrayInitializerExpr().getValues();
                    for (int i = 0; i < thrownExceptions.size(); i++) {
                        javadoc.addBlockTag(thr, thrownExceptions.get(i).asString(),
                                Utilities.replace(exceptionValues.get(i).toString().replace(" + ", "") + "."));
                    } });
    }


    /**
     * defines and sets javadoc @throws tag.
     */
    public void setMethodThrowTag(MethodDeclaration md, Javadoc javadoc, NodeList<MemberValuePair> pairs) {
        NodeList<ReferenceType> thrownExceptions = md.getThrownExceptions();  // gets all thrown exception(s) defined in method signature.
        String thr = Utilities.lowerCaseBlockTag("THROWS");

        pairs.stream()
                .filter(memberValuePair -> memberValuePair.getNameAsString().equals("exceptions"))
                .forEach(memberValuePair -> {
                    NodeList<Expression> exceptionValues = memberValuePair.getValue().asArrayInitializerExpr().getValues();
                    for (int i = 0; i < thrownExceptions.size(); i++) {
                        javadoc.addBlockTag(thr, thrownExceptions.get(i).asString(),
                                Utilities.replace(exceptionValues.get(i).toString().replace(" + ", "") + "."));
                    } });
    }


    /**
     *
     * sets javadoc @see tag
     */
    public void setSeeTag(Javadoc javadoc, NodeList<MemberValuePair> pairs) {
//        this.javadoc = javadoc;
//        this.pairs = pairs;

        String seeTag = Utilities.lowerCaseBlockTag("SEE");

        pairs.stream()
                .filter(memberValuePair -> memberValuePair.getNameAsString().equals("see"))
                .forEach(memberValuePair -> {
                    String value = memberValuePair.getValue().toString();
                    javadoc.addBlockTag(seeTag, Utilities.replace(value)); });
    }

    /**
     *
     * sets javadoc @since tag
     */
    public void setSinceTag(Javadoc javadoc, NodeList<MemberValuePair> pairs) {

        String sinceTag = Utilities.lowerCaseBlockTag("SINCE");

        pairs.stream()
                .filter(memberValuePair -> memberValuePair.getNameAsString().equals("since"))
                .forEach(memberValuePair -> {
                    String value = memberValuePair.getValue().toString();
                    javadoc.addBlockTag(sinceTag, Utilities.replace(value)); });
    }

    /**
     *
     * sets javadoc @serial tag
     */
    public void setSerialTag(Javadoc javadoc, NodeList<MemberValuePair> pairs) {
        String serialTag = Utilities.lowerCaseBlockTag("SERIAL");

        pairs.stream()
                .filter(memberValuePair -> memberValuePair.getNameAsString().equals("serial"))
                .forEach(memberValuePair -> {
                    String value = memberValuePair.getValue().toString();
                    javadoc.addBlockTag(serialTag, Utilities.replace(value));
                });
    }

    /**
     *
     * sets javadoc @serialField tag
     */
    public void setSerialFieldTag(Javadoc javadoc, NodeList<MemberValuePair> pairs) {
        String serialFieldTag = Utilities.replace("serialField");

        pairs.stream()
                .filter(memberValuePair -> memberValuePair.getNameAsString().equals("serialField"))
                .forEach(memberValuePair -> {
                    NodeList<Expression> serialValues = memberValuePair.getValue().asArrayInitializerExpr().getValues();
                    String noCommaValue = serialValues.toString().replace(",", "");
                    String valueWithoutBracketOpener = noCommaValue.replace("[", "");
                    String valueWithoutBracketCloser = valueWithoutBracketOpener.replace("]", "");
                    String finalValue = valueWithoutBracketCloser.replace(" + ", "");
                    javadoc.addBlockTag(serialFieldTag, Utilities.replace(finalValue + ".")); });
    }

    /**
     * defines and sets @serialData tag
     */
    public void setSerialDataTag(Javadoc javadoc, NodeList<MemberValuePair> pairs) {
        String serialDataTag = "serialData";

        pairs.stream()
                .filter(memberValuePair -> memberValuePair.getNameAsString().equals("serialData"))
                .forEach(memberValuePair -> {
                            String value = memberValuePair.getValue().toString();
                            javadoc.addBlockTag(serialDataTag, Utilities.replace(value)); });
    }

    /**
     *
     * sets javadoc @deprecated tag
     */
    public void setDeprecatedTag(Javadoc javadoc, NodeList<MemberValuePair> pairs) {
        String deprecatedTag = Utilities.lowerCaseBlockTag("DEPRECATED");

        pairs.stream()
                .filter(memberValuePair -> memberValuePair.getNameAsString().equals("deprecated"))
                .forEach(memberValuePair -> {
                    String value = memberValuePair.getValue().toString();
                    javadoc.addBlockTag(deprecatedTag, Utilities.replace(value)); });
    }
}
