package org.unidoc.blocktagSetter;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.TypeParameter;
import com.github.javaparser.javadoc.Javadoc;
import com.github.javaparser.javadoc.description.JavadocDescription;
import org.unidoc.annotations.ParamDoc;
import org.unidoc.utils.Utilities;
import java.util.concurrent.atomic.AtomicReference;

/**
 * has methods for transforming unidoc annotations to java doc comments
 */
public class JavadocBlocktagSetter {

    /**
     * defines javadoc description.
     * @return JavadocDescription
     */
    public JavadocDescription setDescription(NodeList<MemberValuePair> pairs) {
        AtomicReference<JavadocDescription> description = new AtomicReference<>();
        pairs.stream().filter(memberValuePair -> memberValuePair.getNameAsString().equals("description")).forEach(memberValuePair -> {
            // .replace(" + ", "\n");
            String value = memberValuePair.getValue().toString();
            String otherValue = Utilities.replace(value);
            String otherValue1 = otherValue.replace(" + ", "\n");
            description.set(JavadocDescription.parseText(otherValue1));
        });
        return description.get();
    }

    /**
     * defines javadoc description for methods
     * @return JavadocDescription
     */
    public JavadocDescription setMethodDescription(MethodDeclaration md, AnnotationExpr annotationExpr, NodeList<MemberValuePair> pairs) {
        AtomicReference<JavadocDescription> description = new AtomicReference<>();
        if (annotationExpr.isNormalAnnotationExpr()) {
            description.set(JavadocDescription.parseText(md.getNameAsString() + "."));
            pairs.stream().filter(memberValuePair -> memberValuePair.getNameAsString().equals("description")).forEach(memberValuePair -> {
                // .replace(" + ", "\n");
                String value = memberValuePair.getValue().toString();
                String otherValue = Utilities.replace(value);
                String otherValue1 = otherValue.replace(" + ", "\n");
                description.updateAndGet(mdValue -> JavadocDescription.parseText(otherValue1 + "."));
            });
        }
        return description.get();
    }

    /**
     * defines javadoc @author tag
     */
    public void setAuthorTag(Javadoc javadoc, NodeList<MemberValuePair> pairs) {
        String authorTag = Utilities.lowerCaseBlockTag("AUTHOR");
        pairs.stream().filter(memberValuePair -> memberValuePair.getNameAsString().equals("author")).forEach(memberValuePair -> {
            NodeList<Expression> values = memberValuePair.getValue().asArrayInitializerExpr().getValues();
            values.forEach(val -> javadoc.addBlockTag(authorTag, Utilities.replace(val.toString())));
        });
    }

    /**
     * defines javadoc @version tag
     */
    public void setVersionTag(Javadoc javadoc, NodeList<MemberValuePair> pairs) {
        String versionTag = Utilities.lowerCaseBlockTag("VERSION");
        pairs.stream().filter(memberValuePair -> memberValuePair.getNameAsString().equals("version")).forEach(memberValuePair -> {
            String value = memberValuePair.getValue().toString();
            javadoc.addBlockTag(versionTag, Utilities.replace(value));
        });
    }

    /**
     * defines javadoc @param tag for class.
     */
    public void setClassParamTag(ClassOrInterfaceDeclaration cd, AnnotationExpr annotationExpr, Javadoc javadoc) {
        // gets constructor's parameter(s)
        NodeList<TypeParameter> typeParameters = cd.getTypeParameters();
        String annotation = Utilities.lowerCaseBlockTag("PARAM");
        if (annotationExpr.isAnnotationExpr()) {
            for (TypeParameter parameter : typeParameters) {
                parameter.getAnnotationByClass(ParamDoc.class).ifPresent(ParamAnnotationExpr -> {
                    // get MemberValuePairs (description = ...)
                    NodeList<MemberValuePair> paramPairs = ParamAnnotationExpr.asNormalAnnotationExpr().getPairs();
                    paramPairs.stream().filter(paramPair -> paramPair.getNameAsString().equals("description")).forEach(memberValuePair -> {
                        String value = memberValuePair.getValue().toString();
                        String otherValue = Utilities.replace(value);
                        String otherValue1 = otherValue.replace(" + ", "\n");
                        javadoc.addBlockTag(annotation, parameter.getNameAsString(), otherValue1);
                    });
                    ParamAnnotationExpr.remove();
                });
            }
        }
    }

    /**
     * defines javadoc @param tag for interface.
     */
    public void setInterfaceParamTag(ClassOrInterfaceDeclaration id, AnnotationExpr annotationExpr, Javadoc javadoc) {
        // gets constructor's parameter(s)
        NodeList<TypeParameter> typeParameters = id.getTypeParameters();
        String annotation = Utilities.lowerCaseBlockTag("PARAM");
        if (annotationExpr.isAnnotationExpr()) {
            for (TypeParameter parameter : typeParameters) {
                parameter.getAnnotationByClass(ParamDoc.class).ifPresent(ParamAnnotationExpr -> {
                    // get MemberValuePairs (description = ...)
                    NodeList<MemberValuePair> paramPairs = ParamAnnotationExpr.asNormalAnnotationExpr().getPairs();
                    paramPairs.stream().filter(paramPair -> paramPair.getNameAsString().equals("description")).forEach(memberValuePair -> {
                        String value = memberValuePair.getValue().toString();
                        String otherValue = Utilities.replace(value);
                        String otherValue1 = otherValue.replace(" + ", "\n");
                        javadoc.addBlockTag(annotation, parameter.getNameAsString(), otherValue1);
                    });
                    ParamAnnotationExpr.remove();
                });
            }
        }
    }

    /**
     * defines javadoc @param tag for constructors.
     */
    public void setConstructorParamTag(ConstructorDeclaration cd, AnnotationExpr annotationExpr, Javadoc javadoc) {
        // gets constructor's parameter(s)
        NodeList<Parameter> parameters = cd.getParameters();
        String annotation = Utilities.lowerCaseBlockTag("PARAM");
        if (annotationExpr.isAnnotationExpr()) {
            for (Parameter parameter : parameters) {
                parameter.getAnnotationByClass(ParamDoc.class).ifPresent(ParamAnnotationExpr -> {
                    // get MemberValuePairs (description = ...)
                    NodeList<MemberValuePair> paramPairs = ParamAnnotationExpr.asNormalAnnotationExpr().getPairs();
                    paramPairs.stream().filter(paramPair -> paramPair.getNameAsString().equals("description")).forEach(memberValuePair -> {
                        String value = memberValuePair.getValue().toString();
                        String otherValue = Utilities.replace(value);
                        String otherValue1 = otherValue.replace(" + ", "\n");
                        javadoc.addBlockTag(annotation, parameter.getNameAsString(), otherValue1);
                    });
                    ParamAnnotationExpr.remove();
                });
            }
        }
    }

    /**
     * defines javadoc @param tag for methods.
     */
    public void setMethodParamTag(MethodDeclaration md, AnnotationExpr annotationExpr, Javadoc javadoc) {
        // gets constructor's parameter(s)
        NodeList<Parameter> parameters = md.getParameters();
        String annotation = Utilities.lowerCaseBlockTag("PARAM");
        if (annotationExpr.isAnnotationExpr()) {
            for (Parameter parameter : parameters) {
                parameter.getAnnotationByClass(ParamDoc.class).ifPresent(ParamAnnotationExpr -> {
                    // get MemberValuePairs (description = ...)
                    NodeList<MemberValuePair> paramPairs = ParamAnnotationExpr.asNormalAnnotationExpr().getPairs();
                    paramPairs.stream().filter(paramPair -> paramPair.getNameAsString().equals("description")).forEach(memberValuePair -> {
                        String value = memberValuePair.getValue().toString();
                        String otherValue = Utilities.replace(value);
                        String otherValue1 = otherValue.replace(" + ", "\n");
                        javadoc.addBlockTag(annotation, parameter.getNameAsString(), otherValue1);
                    });
                    ParamAnnotationExpr.remove();
                });
            }
        }
    }

    /**
     * defines @return tag
     */
    public void setMethodReturnTag(MethodDeclaration md, Javadoc javadoc, NodeList<MemberValuePair> pairs) {
        String returns = Utilities.lowerCaseBlockTag("RETURN");
        if (!md.getType().isVoidType()) {
            // if method returns a value i.e NOT void.
            pairs.stream().filter(memberValuePair -> memberValuePair.getNameAsString().equals("returns")).forEach(memberValuePair -> {
                String value = memberValuePair.getValue().toString();
                String otherValue = Utilities.replace(value);
                String otherValue1 = otherValue.replace(" + ", "\n");
                javadoc.addBlockTag(returns, otherValue1);
            });
        }
    }

    /**
     * defines @return tag
     */
    public void setAnnotationMemberReturnTag(AnnotationMemberDeclaration aed, Javadoc javadoc, NodeList<MemberValuePair> pairs) {
        String returns = Utilities.lowerCaseBlockTag("RETURN");
        if (!aed.getType().isVoidType()) {
            pairs.stream().filter(memberValuePair -> memberValuePair.getNameAsString().equals("returns")).forEach(memberValuePair -> {
                String value = memberValuePair.getValue().toString();
                String otherValue = Utilities.replace(value);
                String otherValue1 = otherValue.replace(" + ", "\n");
                javadoc.addBlockTag(returns, otherValue1);
            });
        }
    }

    /**
     * defines javadoc @throws tag for constructors.
     */
    public void setConstructorThrowTag(ConstructorDeclaration cd, Javadoc javadoc, NodeList<MemberValuePair> pairs) {
        // gets all thrown exception(s) defined in method signature.
        NodeList<ReferenceType> thrownExceptions = cd.getThrownExceptions();
        String thr = Utilities.lowerCaseBlockTag("THROWS");
        pairs.stream().filter(memberValuePair -> memberValuePair.getNameAsString().equals("exceptions")).forEach(memberValuePair -> {
            NodeList<Expression> exceptionValues = memberValuePair.getValue().asArrayInitializerExpr().getValues();
            for (int i = 0; i < thrownExceptions.size(); i++) {
                javadoc.addBlockTag(thr, thrownExceptions.get(i).asString(), Utilities.replace(exceptionValues.get(i).toString().replace(" + ", "\n") + "."));
            }
        });
    }

    /**
     * sets javadoc @throws tag for methods.
     */
    public void setMethodThrowTag(MethodDeclaration md, Javadoc javadoc, NodeList<MemberValuePair> pairs) {
        // gets all thrown exception(s) defined in method signature.
        NodeList<ReferenceType> thrownExceptions = md.getThrownExceptions();
        String thr = Utilities.lowerCaseBlockTag("THROWS");
        pairs.stream().filter(memberValuePair -> memberValuePair.getNameAsString().equals("exceptions")).forEach(memberValuePair -> {
            NodeList<Expression> exceptionValues = memberValuePair.getValue().asArrayInitializerExpr().getValues();
            for (int i = 0; i < thrownExceptions.size(); i++) {
                javadoc.addBlockTag(thr, thrownExceptions.get(i).asString(), Utilities.replace(exceptionValues.get(i).toString().replace(" + ", "\n") + "."));
            }
        });
    }

    /**
     * defines javadoc @see tag
     */
    public void setSeeTag(Javadoc javadoc, NodeList<MemberValuePair> pairs) {
        String seeTag = Utilities.lowerCaseBlockTag("SEE");
        pairs.stream().filter(memberValuePair -> memberValuePair.getNameAsString().equals("see")).forEach(memberValuePair -> {
            String value = memberValuePair.getValue().toString();
            String otherValue = Utilities.replace(value);
            String otherValue1 = otherValue.replace(" + ", "");
            javadoc.addBlockTag(seeTag, otherValue1);
        });
    }

    /**
     * defines javadoc @since tag
     */
    public void setSinceTag(Javadoc javadoc, NodeList<MemberValuePair> pairs) {
        String sinceTag = Utilities.lowerCaseBlockTag("SINCE");
        pairs.stream().filter(memberValuePair -> memberValuePair.getNameAsString().equals("since")).forEach(memberValuePair -> {
            String value = memberValuePair.getValue().toString();
            javadoc.addBlockTag(sinceTag, Utilities.replace(value));
        });
    }

    /**
     * defines javadoc @serial tag
     */
    public void setSerialTag(Javadoc javadoc, NodeList<MemberValuePair> pairs) {
        String serialTag = Utilities.lowerCaseBlockTag("SERIAL");
        pairs.stream().filter(memberValuePair -> memberValuePair.getNameAsString().equals("serial")).forEach(memberValuePair -> {
            String value = memberValuePair.getValue().toString();
            String otherValue = Utilities.replace(value);
            String otherValue1 = otherValue.replace(" + ", "\n");
            javadoc.addBlockTag(serialTag, Utilities.replace(otherValue1));
        });
    }

    /**
     * defines javadoc @serialField tag
     */
    public void setSerialFieldTag(Javadoc javadoc, NodeList<MemberValuePair> pairs) {
        String serialFieldTag = "serialField";
        pairs.stream().filter(memberValuePair -> memberValuePair.getNameAsString().equals("serialField")).forEach(memberValuePair -> {
            NodeList<Expression> serialValues = memberValuePair.getValue().asArrayInitializerExpr().getValues();
            String noCommaValue = serialValues.toString().replace(",", "");
            String valueWithoutBracketOpener = noCommaValue.replace("[", "");
            String valueWithoutBracketCloser = valueWithoutBracketOpener.replace("]", "");
            String otherValue = valueWithoutBracketCloser.replace(" + ", "\n");
            javadoc.addBlockTag(serialFieldTag, Utilities.replace(otherValue + "."));
        });
    }

    /**
     * defines @serialData tag
     */
    public void setSerialDataTag(Javadoc javadoc, NodeList<MemberValuePair> pairs) {
        String serialDataTag = "serialData";
        pairs.stream().filter(memberValuePair -> memberValuePair.getNameAsString().equals("serialData")).forEach(memberValuePair -> {
            String value = memberValuePair.getValue().toString();
            String otherValue = Utilities.replace(value);
            String otherValue1 = otherValue.replace(" + ", "\n");
            javadoc.addBlockTag(serialDataTag, otherValue1);
        });
    }

    /**
     * defines @hidden tag
     */
    public void setHiddenTag(Javadoc javadoc, NodeList<MemberValuePair> pairs) {
        String hiddenTag = "hidden";
        pairs.stream().filter(memberValuePair -> memberValuePair.getNameAsString().equals("hidden")).forEach(memberValuePair -> {
            javadoc.addBlockTag(hiddenTag);
        });
    }

    /**
     * defines javadoc @provides tag
     */
    public void setProvidesTag(Javadoc javadoc, NodeList<MemberValuePair> pairs) {
        String providesTag = "PROVIDES";
        pairs.stream().filter(memberValuePair -> memberValuePair.getNameAsString().equals("provides")).forEach(memberValuePair -> {
            NodeList<Expression> providesValues = memberValuePair.getValue().asArrayInitializerExpr().getValues();
            String noCommaValue = providesValues.toString().replace(",", "");
            String valueWithoutBracketOpener = noCommaValue.replace("[", "");
            String valueWithoutBracketCloser = valueWithoutBracketOpener.replace("]", "");
            String otherValue = valueWithoutBracketCloser.replace(" + ", "\n");
            javadoc.addBlockTag(providesTag, Utilities.replace(otherValue + "."));
        });
    }

    /**
     * defines javadoc @uses tag
     */
    public void setUsesTag(Javadoc javadoc, NodeList<MemberValuePair> pairs) {
        String usesTag = "USES";
        pairs.stream().filter(memberValuePair -> memberValuePair.getNameAsString().equals("uses")).forEach(memberValuePair -> {
            NodeList<Expression> usesValues = memberValuePair.getValue().asArrayInitializerExpr().getValues();
            String noCommaValue = usesValues.toString().replace(",", "");
            String valueWithoutBracketOpener = noCommaValue.replace("[", "");
            String valueWithoutBracketCloser = valueWithoutBracketOpener.replace("]", "");
            String otherValue = valueWithoutBracketCloser.replace(" + ", "\n");
            javadoc.addBlockTag(usesTag, Utilities.replace(otherValue + "."));
        });
    }

    /**
     * defines javadoc @deprecated tag
     */
    public void setDeprecatedTag(Javadoc javadoc, NodeList<MemberValuePair> pairs) {
        String deprecatedTag = Utilities.lowerCaseBlockTag("DEPRECATED");
        pairs.stream().filter(memberValuePair -> memberValuePair.getNameAsString().equals("deprecated")).forEach(memberValuePair -> {
            String value = memberValuePair.getValue().toString();
            String otherValue = Utilities.replace(value);
            String otherValue1 = otherValue.replace(" + ", "\n");
            javadoc.addBlockTag(deprecatedTag, otherValue1);
        });
    }
}
