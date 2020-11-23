package org.unidoc.parse;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.nodeTypes.NodeWithJavadoc;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.base.Strings;
import org.unidoc.annotations.PackageDoc;
import org.unidoc.core.PackageDocumentation;

import java.util.List;
import java.util.Optional;

public class PackageParser extends VoidVisitorAdapter<Void> {

    /**
     * Converts unidoc package annotations to java doc comments.
     * Also removes @PackageDoc annotations from source code
     *
     * @param pd package(s) to be accessed
     * @param arg void
     */
    @Override
    public void visit(PackageDeclaration pd, Void arg) {
        super.visit(pd, arg);
        PackageDocumentation packageDocumentation = new PackageDocumentation(pd);
        if(pd.isAnnotationPresent(PackageDoc.class)) {
            pd.setComment(packageDocumentation.getJavadoc());
            pd.getAnnotationByClass(PackageDoc.class).ifPresent(Node::remove);
        } else {
            System.out.println(pd.toString());
            System.out.println("annotation not found");
        }

    }
    
//    @Override
//    public void visit(CompilationUnit cu, Void arg) {
//        super.visit(cu, arg);
//        List<PackageDeclaration> packageDeclarations = cu.findAll(PackageDeclaration.class);
//
//        for (PackageDeclaration pd : packageDeclarations) {
//
//            PackageDocumentation packageDocumentation = new PackageDocumentation(pd);
//            pd.getAnnotationByClass(PackageDoc.class).ifPresent(packageAnnotationExpr -> packageAnnotationExpr.remove());
//            pd.setComment(packageDocumentation.getJavadoc());
//
//        }

//    }

}
