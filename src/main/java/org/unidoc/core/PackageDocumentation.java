package org.unidoc.core;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.javadoc.Javadoc;
import com.github.javaparser.javadoc.description.JavadocDescription;
import org.unidoc.annotations.PackageDoc;
import org.unidoc.blocktagSetter.JavadocBlocktagSetter;
import org.unidoc.utils.Utilities;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * has methods for transforming @PackageDoc annotations to java doc comments
 */
public class PackageDocumentation {

    private NodeList<MemberValuePair> pairs;
    private Javadoc javadoc;
    private JavadocBlocktagSetter javadocBlocktagSetter = new JavadocBlocktagSetter();

    /**
     * assigns value to pairs
     * @param pd package declaration
     */
    public PackageDocumentation(PackageDeclaration pd) {
        Optional<AnnotationExpr> annotationByClass = pd.getAnnotationByClass(PackageDoc.class);
        annotationByClass.ifPresent(annotationExpr -> {
            this.pairs = annotationExpr.asNormalAnnotationExpr().getPairs();
        });
    }

    /**
     * sets description of package
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
    }

    /**
     *
     * sets javadoc @version tag
     */
    public void versionTag() {
        javadocBlocktagSetter.setVersionTag(javadoc, pairs);
    }

    /**
     *
     * sets javadoc @see tag
     */
    public void seeTag() {
        javadocBlocktagSetter.setSeeTag(javadoc, pairs);
    }

    /**
     *
     * sets javadoc @since tag
     */
    public void sinceTag() {
        javadocBlocktagSetter.setSinceTag(javadoc, pairs);
    }

    /**
     * calls methods for setting javadoc comments.
     * @return javadoc
     */
    public JavadocComment getJavadoc() {
        javadoc = new Javadoc(description());
        authorTag();
        versionTag();
        seeTag();
        sinceTag();
        return javadoc.toComment();
    }


//    public JavadocComment get() {
//        JavadocComment jdc = new JavadocComment("fuck this");
//        return jdc;
//    }
}
