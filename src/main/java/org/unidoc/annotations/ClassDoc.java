package org.unidoc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * for documenting a class declaration
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ClassDoc {

    /**
     * @return description
     */
    String description() default "";

    /**
     * @return author name(s)
     */
    String[] author() default "";

    /**
     * @return version
     */
    String version() default "";

    /**
     * @return associated links and references
     */
    String see() default "";

    /**
     * @return when class was created
     */
    String since() default "";

    /**
     * @return field name, field type, field-description
     */
    String serialField() default "";

    /**
     * declares that the class shouldn't be documented
     */
    String hidden() default "";

    /**
     * recommends that a class shouldn't be used
     * @return why the class shouldn't be used and any reference
     */
    // @deprecated
    String deprecated() default "";
}
