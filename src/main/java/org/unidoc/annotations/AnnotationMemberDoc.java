package org.unidoc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * for documenting an annotation member declaration
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AnnotationMemberDoc {

    /**
     * @return description
     */
    String description() default "";

    /**
     * @return version
     */
    String version() default "";

    /**
     * @return info about return type
     */
    String returns() default "";

    /**
     * @return associated links and references
     */
    String see() default "";

    /**
     * @return when annotation member was created
     */
    String since() default "";

    /**
     * declares that the annotation member shouldn't be documented
     */
    String hidden() default "";

    /**
     * recommends that a annotation member shouldn't be used
     * @return why the annotation member shouldn't be used and any reference
     */
    String deprecated() default "";
}
