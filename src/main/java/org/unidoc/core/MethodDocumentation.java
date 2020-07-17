package org.unidoc.core;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.javadoc.Javadoc;
import com.github.javaparser.javadoc.JavadocBlockTag;
import com.github.javaparser.javadoc.description.JavadocDescription;
import org.unidoc.MethodDoc;

public class MethodDocumentation {

  private final Javadoc javadoc;
  private JavadocDescription description;
//    private Log log;

    private NodeList<MemberValuePair> values;
    private MethodDeclaration md;

    public MethodDocumentation(MethodDeclaration md) {
        this.md = md;
        md.getAnnotationByClass(MethodDoc.class).ifPresent(annotation -> {
            this.values = annotation.asNormalAnnotationExpr().getPairs();
        });
        javadoc = new Javadoc(setJavaDocDescription());
        setReturnTag();
    }

    @MethodDoc(returns = "description of method")
    protected JavadocDescription setJavaDocDescription() {
        values.forEach(v -> {
            if (v.getNameAsString().equals("value")) {
               description = JavadocDescription.parseText(v.getValue().toString().replace("\"", "") + ".");
            }
        });
        return description;
    }

    private void setReturnTag() {
        values.forEach(v -> {
            if (v.getNameAsString().equals("returns")) {
                if (v.getValue() == null) {
                    javadoc.addBlockTag(JavadocBlockTag.Type.RETURN.name().toLowerCase(), " ".replace("\"", ""));
                } else {
                    javadoc.addBlockTag(JavadocBlockTag.Type.RETURN.name().toLowerCase(),
                            v.getValue()
                                    .toString()
                                    .replace("\"", ""));
                }
            }
        });
    }

    public Javadoc getJavadoc() {
        return javadoc;
    }
}
