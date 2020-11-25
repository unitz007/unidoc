package org.unidoc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface AnnotationDoc {

    /**
     *
     * @return description
     */
    String description() default "";

    /**
     *
     * @return author name(s)
     */
    String[] author() default "";

    /**
     *
     * @return version
     */
    String version() default "";

    /**
     *
     * @return associated links and references
     */
    String see() default "";

    /**
     *
     * @return when annotation was created
     */
    String since() default "";

    /**
     * declares that the annotation shouldn't be documented
     *
     */
    String hidden() default "";

    /**
     *
     * @return field name, type and description
     */
    String[] serialField() default "";

    /**
     * recommends that an annotation shouldn't be used
     * @return why the annotation shouldn't be used and any reference
     */
    String deprecated() default ""; // @deprecated
}
