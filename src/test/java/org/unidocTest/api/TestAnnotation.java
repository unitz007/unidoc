package org.unidocTest.api;

import org.unidoc.annotations.AnnotationDoc;
import org.unidoc.annotations.AnnotationMemberDoc;

@AnnotationDoc(description = "...")
public @interface TestAnnotation {

    @AnnotationMemberDoc(description = "...")
    String blah() default "";
}
