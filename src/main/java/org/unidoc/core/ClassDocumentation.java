package org.unidoc.core;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.javadoc.Javadoc;
import com.github.javaparser.javadoc.description.JavadocDescription;
import com.github.javaparser.javadoc.description.JavadocDescriptionElement;
import org.unidoc.ClassDoc;
import org.unidoc.utils.Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class ClassDocumentation {

    private NodeList<MemberValuePair> pairs;
    private Javadoc javadoc;

    /**
     *
     * @param cd class declaration
     */
    public ClassDocumentation(ClassOrInterfaceDeclaration cd) {
        Optional<AnnotationExpr> annotationByClass = cd.getAnnotationByClass(ClassDoc.class);
        annotationByClass.ifPresent(annotationExpr -> {
            this.pairs = annotationExpr.asNormalAnnotationExpr().getPairs();
        });
    }

    /**
     *
     * sets javadoc @author tag
     */
    private void author() {
        String authorTag = Utilities.lowerCaseBlockTag("AUTHOR");

        pairs.stream()
                .filter(memberValuePair -> memberValuePair.getNameAsString().equals("author"))
                .forEach(memberValuePair -> {
                        NodeList<Expression> values = memberValuePair.getValue()
                                                        .asArrayInitializerExpr().getValues();
                        values.forEach(val -> javadoc.addBlockTag(authorTag, Utilities.replace(val.toString())));
                        });
    }

    /**
     *
     * sets javadoc @version tag
     */
    public void version() {
        String versionTag = Utilities.lowerCaseBlockTag("VERSION");

        pairs.stream()
                .filter(memberValuePair -> memberValuePair.getNameAsString().equals("version"))
                .forEach(memberValuePair -> {
                        String value = memberValuePair.getValue().toString();
                        javadoc.addBlockTag(versionTag, Utilities.replace(value));
                });
    }

    /**
     *
     * @return javadoc description
     */
    private JavadocDescription description() {
        List<String> lines = new ArrayList<>();
        //List<String> line = new ArrayList<>();
        AtomicReference<JavadocDescription> description = new AtomicReference<>();
//        for (MemberValuePair memberValuePair : pairs) {
//            if (memberValuePair.getNameAsString().equals("description")) {
//                String desc = memberValuePair.getValue().toString();
//                String[] split = desc.split("\\+");
//                List.of(split).forEach(s -> {
//                    System.out.println(Utilities.replace(s.trim()));
//                });
//                description = JavadocDescription.parseText(Utilities.replace( desc + "."));
//            }
//        }
        pairs.stream()
                .filter(memberValuePair -> memberValuePair.getNameAsString().equals("description"))
                .forEach(memberValuePair -> {
                    String[] desc = memberValuePair.getValue().toString().split("\\+");
                    Arrays.stream(desc).forEach(System.out::println);
                    description.set(JavadocDescription.parseText(Utilities.replace(memberValuePair.getValue() + ".")));

                });
        return description.get();
    }

    /**
     *
     * @return javadoc
     */
    public Javadoc getJavadoc() {
        javadoc = new Javadoc(description());
        author();
        version();
        return javadoc;
    }
}