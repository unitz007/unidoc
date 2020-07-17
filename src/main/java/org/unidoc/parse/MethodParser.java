package org.unidoc.parse;

import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.javadoc.Javadoc;
import com.github.javaparser.javadoc.JavadocBlockTag;
import com.github.javaparser.javadoc.description.JavadocDescription;
import com.github.javaparser.resolution.SymbolResolver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.unidoc.MethodDoc;
import org.unidoc.core.MethodDocumentation;

public class MethodParser extends VoidVisitorAdapter<Void> {

    private final Log log = LogFactory.getLog(this.getClass());

    public void visit(MethodDeclaration md, Void arg) {

        super.visit(md, arg);
        if (md.isAnnotationPresent(MethodDoc.class)) {
            MethodDocumentation methodDocumentation = new MethodDocumentation(md);
            md.setJavadocComment(methodDocumentation.getJavadoc());
            System.out.println(md);
        } else {
            log.info(md.getNameAsString() + "() is not annotated with @" + MethodDoc.class.getSimpleName());
        }
    }


}
