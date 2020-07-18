package org.unidoc.core;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.javadoc.Javadoc;
import com.github.javaparser.javadoc.JavadocBlockTag;
import com.github.javaparser.javadoc.description.JavadocDescription;
import org.unidoc.MethodDoc;
import org.unidoc.utils.Utilities;

public class MethodDocumentation {

  private Javadoc javadoc;
//    private Log log;

    private NodeList<MemberValuePair> values;
    private final MethodDeclaration md;

    /**
     * constructor with one parameter.
     * @param md method declaration
     */
    public MethodDocumentation(MethodDeclaration md) {
        this.md = md;

        // checks if method has @MethodDoc annotation
        md.getAnnotationByClass(MethodDoc.class).ifPresent(annotation -> {
            this.values = annotation.asNormalAnnotationExpr().getPairs();
        });


    }

    /**
     * creates and return an instance of JavadocDescription.
     * @return an instance of JavadocDescription
     */
    @MethodDoc(description = "scbdhu", returns = "description of method")
    protected JavadocDescription docDescription() {
        JavadocDescription description = new JavadocDescription();
        for (MemberValuePair v: values) {
            if (v.getNameAsString().equals("description")) { // checks for description field in @MethodDoc annotation
                description = JavadocDescription.parseText(Utilities.replace(v.getValue().toString()) + ".");
            }
        }
            return description;
    }

    /**
     * defines and set @return tag
     */
    private void returnTag() {
        if (!md.getType().isVoidType()) {  // if method returns a value i.e NOT void.
            values.forEach(v -> {
                if (v.getNameAsString().equals("returns")) {  // find returns value in MethodClass annotation
                    JavadocBlockTag.Type returns = JavadocBlockTag.Type.RETURN;
                        javadoc.addBlockTag(Utilities.lowerCaseBlockTag(returns), Utilities.replace(v.getValue().toString()));
                    }
            });
        }
    }

    /**
     * defines and sets javadoc @throws tag.
     */
    private void throwTag() {
        NodeList<ReferenceType> thrownExceptions = md.getThrownExceptions();  // gets all thrown exception(s) defined in method signature.
        if (!thrownExceptions.isEmpty()) {
            values.forEach(value -> {
                if (value.getNameAsString().equals("exceptions")) {
                    if (!value.getValue().isArrayInitializerExpr()) {  // if field is not defined as an array i.e exceptions = "exception".
                        javadoc.addBlockTag(Utilities.lowerCaseBlockTag(JavadocBlockTag.Type.THROWS),
                                Utilities.replace(thrownExceptions.get(0).asString() + " " + value.getValue())); // add @throws to javadoc
                    } else {  // if field is defined as an array i.e exceptions = { "exception1", "exception2", ...exception(n) }
                        NodeList<Expression> val = value.getValue().asArrayInitializerExpr().getValues(); // get exception values as arrayInitializer
                        for (int i = 0; i < thrownExceptions.size(); i++) {
                            javadoc.addBlockTag(Utilities.lowerCaseBlockTag(JavadocBlockTag.Type.THROWS),
                                    Utilities.replace(thrownExceptions.get(i).asString() + " " + val.get(i).toString())); // add @throws to javadoc
                        }
                    }
                }
            });
        }
    }

    /**
     * defines and set javadoc @param tag.
     */
    private void paramTag() {
        NodeList<Parameter> parameters = md.getParameters(); // gets method's parameter(s).
        if (!parameters.isEmpty()) {
           values.forEach(value -> {
               if (value.getNameAsString().equals("params")) {
                   if (!value.getValue().isArrayInitializerExpr()) { // if field is not defined as an array i.e params = "param".
                       javadoc.addBlockTag(Utilities.lowerCaseBlockTag(JavadocBlockTag.Type.PARAM),
                               Utilities.replace(parameters.get(0).getNameAsString() + " " + value.getValue())); // add @param to javadoc
                   } else { // if field is defined as an array i.e params = { "param1", "param2", ...param(n) }
                       NodeList<Expression> val = value.getValue().asArrayInitializerExpr().getValues(); // get params values as arrayInitializer
                       for (int i = 0; i < parameters.size(); i++) {
                           javadoc.addBlockTag(Utilities.lowerCaseBlockTag(JavadocBlockTag.Type.PARAM),
                                   Utilities.replace(parameters.get(i).getNameAsString() + " " + val.get(i).toString())); // add @param to javadoc
                       }
                   }
               }
           });
        }
    }

    /**
     * defines and set javadoc.
     * @return Javadoc
     */
    public Javadoc getJavadoc() {
        javadoc = new Javadoc(docDescription()); // instantiates javadoc.
        paramTag(); // add @param tag
        returnTag(); // add @return tag
        throwTag(); // add @throws tag
        return javadoc;
    }
}
