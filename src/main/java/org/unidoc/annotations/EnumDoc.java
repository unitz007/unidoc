package org.unidoc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * for documenting an enum declaration
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EnumDoc {

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
     * @return when enum was created
     */
    String since() default "";

    /**
     * declares that the enum shouldn't be documented
     */
    String hidden() default "";

    /**
     * @return field name, type and description
     */
    String[] serialField() default "";

    /**
     * recommends that an enum shouldn't be used
     * @return why the enum shouldn't be used and any reference
     */
    // @deprecated
    String deprecated() default "";
}
