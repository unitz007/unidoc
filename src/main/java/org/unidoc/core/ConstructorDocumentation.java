package org.unidoc.core;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.javadoc.Javadoc;
import com.github.javaparser.javadoc.description.JavadocDescription;
import org.unidoc.ConstructorDoc;
import org.unidoc.FieldDoc;
import org.unidoc.MethodDoc;
import org.unidoc.utils.Utilities;

import java.lang.reflect.Constructor;

public class ConstructorDocumentation {

    private Javadoc javadoc;

    private NodeList<MemberValuePair> values;
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
                this.values = annotation.asNormalAnnotationExpr().getPairs();
            }
            this.annotationExpr = annotation;
        });
    }

    /**
     * sets description of constructor
     *
     */
    protected JavadocDescription description() {
        JavadocDescription description = null;
        for (MemberValuePair value : values) {
            description = JavadocDescription.parseText(Utilities.replace(value.getValue().toString()));
        }
        return description;
    }

    /**
     * defines and set javadoc @param tag.
     */
    @MethodDoc
    private void paramTag() {
        NodeList<Parameter> parameters = cd.getParameters(); // gets method's parameter(s)
        String annotation = Utilities.lowerCaseBlockTag("PARAM");

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
                    annotExpr.remove();
                });
            }
        }
    }

    /**
     * defines and set javadoc.
     * @return Javadoc
     */
    public Javadoc getJavadoc() {
        javadoc = new Javadoc(description());
        paramTag();
        return javadoc;
    }

//    /**
//     * checks if class has @ConstructorDoc annotations.
//     * @param constructor constructor
//     * @return true if its annotated
//     */
//    public boolean isAnnotated(Constructor<?> constructor) {
//       return constructor.isAnnotationPresent(ConstructorDoc.class);
//    }
}
