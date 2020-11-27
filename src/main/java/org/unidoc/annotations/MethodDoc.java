package org.unidoc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MethodDoc {

    /**
     *
     * @return description
     */
    String description() default ""; // description

    /**
     *
     * @return version
     */
    String version() default "";

    /**
     *
     * @return info about return type
     */
    String returns() default "";

    /**
     *
     * @return exception(s) thrown.
     */
    String[] exceptions() default "";

    /**
     *
     * @return associated links and references
     */
    String see() default "";

    /**
     *
     * @return when method was created
     */
    String since() default "";

    /**
     *
     * @return description of the data to be serialized, including it's type and order
     */
    String serialData() default "";

    /**
     * declares that the method shouldn't be documented
     *
     */
    String hidden() default "";

    /**
     * recommends that a class shouldn't be used
     * @return why the method shouldn't be used and any reference
     */
    String deprecated() default "";
}
