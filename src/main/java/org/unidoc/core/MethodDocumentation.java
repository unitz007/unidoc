package org.unidoc.core;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.javadoc.Javadoc;
import com.github.javaparser.javadoc.description.JavadocDescription;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.unidoc.FieldDoc;
import org.unidoc.MethodDoc;
import org.unidoc.utils.Utilities;

public class MethodDocumentation {

  private Javadoc javadoc;
    private Log log = LogFactory.getLog(this.getClass());


    private NodeList<MemberValuePair> values;
    private final MethodDeclaration md;
    private AnnotationExpr annotationExpr;

    /**
     * constructor with one parameter.
     * @param md method declaration
     */
    public MethodDocumentation(MethodDeclaration md) {
        this.md = md;
        // checks if method has @MethodDoc annotation
        md.getAnnotationByClass(MethodDoc.class).ifPresent(annotation -> {
                if (annotation.isNormalAnnotationExpr()) {
                    this.values = annotation.asNormalAnnotationExpr().getPairs();
                }
                this.annotationExpr = annotation;
        });
    }

    /**
     * creates and return an instance of JavadocDescription.
     * @return an instance of JavadocDescription
     */
    @MethodDoc(description = "scbdhu", returns = "description of method")
    protected JavadocDescription docDescription() {
        JavadocDescription description = null;
        if (annotationExpr.isAnnotationExpr()) {
            description = JavadocDescription.parseText(md.getNameAsString() + ".");
        }
        if (annotationExpr.isNormalAnnotationExpr()) {
            for (MemberValuePair v : values) {
                if (v.getNameAsString().equals("description")) { // checks for description field in @MethodDoc annotation
                    description = JavadocDescription.parseText(Utilities.replace(v.getValue().toString()) + ".");
                }
            }
        }
        return description;
    }

    /**
     * defines and set @return tag
     */
    private void returnTag() {
        String returns = Utilities.lowerCaseBlockTag("RETURN");
        if (!md.getType().isVoidType()) {  // if method returns a value i.e NOT void.
            if (annotationExpr.isNormalAnnotationExpr()) {
                values.forEach(v -> {
                    if (v.getNameAsString().equals("returns")) {  // find returns value in MethodClass annotation
                        javadoc.addBlockTag(returns, "", Utilities.replace(v.getValue().toString()));
                    }
                });
            } else  {
                javadoc.addBlockTag(returns, "");
            }
        }
    }

    /**
     * defines and sets javadoc @throws tag.
     */
    private void throwTag() {
        NodeList<ReferenceType> thrownExceptions = md.getThrownExceptions();  // gets all thrown exception(s) defined in method signature.
        String thr = Utilities.lowerCaseBlockTag("THROWS");
        if (!thrownExceptions.isEmpty()) {
             if (annotationExpr.isNormalAnnotationExpr()) { // if annotation is normal expression
                values.forEach(value -> {
                    if (value.getNameAsString().equals("exceptions")) {
                        if (!value.getValue().isArrayInitializerExpr()) {  // if field is not defined as an array i.e exceptions = "exception".
                            javadoc.addBlockTag(thr,
                                    Utilities.replace(thrownExceptions.get(0).asString() + " " + value.getValue())); // add @throws to javadoc
                        } else {  // if field is defined as an array i.e exceptions = { "exception1", "exception2", ...exception(n) }
                            NodeList<Expression> val = value.getValue()
                                    .asArrayInitializerExpr().getValues(); // get exception values as arrayInitializer
                            for (int i = 0; i < thrownExceptions.size(); i++) {
                                javadoc.addBlockTag(thr, thrownExceptions.get(i)
                                                .asString(), Utilities.replace(val.get(i).toString())); // add @throws to javadoc
                            }
                        }
                    }
                });
            } else {
                thrownExceptions.forEach(exception -> {
                    javadoc.addBlockTag(thr, exception.asString());
                });
            }
        }
    }

    /**
     * defines and set javadoc @param tag.
     */
    private void paramTag() {
        NodeList<Parameter> parameters = md.getParameters(); // gets method's parameter(s).
        String annotation = Utilities.lowerCaseBlockTag("PARAM");
        String param = "";
        if (annotationExpr.isAnnotationExpr()) {
            for (Parameter parameter: parameters) {
                parameter.getAnnotationByClass(FieldDoc.class).ifPresent(annotExpr -> {
                    if (annotExpr.isNormalAnnotationExpr()) {
                        annotExpr.asNormalAnnotationExpr().getPairs().forEach(memberValuePair -> {
                            if (memberValuePair.getNameAsString().equals("description")) {
                                String p = memberValuePair.getValue().toString();
                                javadoc.addBlockTag(annotation, parameter.getNameAsString(), Utilities.replace(p));
                            }
                        });
                    }
                });
            }
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
