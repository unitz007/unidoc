package org.unidoc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.CONSTRUCTOR)
public @interface ConstructorDoc {

    /**
     *
     * @return description of constructor
     */
    String description() default ""; // description

    /**
     *
     * @return params
     */
    String[] params() default "";

    /**
     *
     * @return description of return value
     */
    String returns() default "";

    /**
     *
     * @return @see in javadoc
     */
    String see() default "";

    /**
     *
     * @return @since in javadoc
     */
    String since() default "";

    /**
     *
     * @return @serialData in javadoc
     */
    String serialData() default "";

    /**
     *
     * @return exceptions
     */
    String[] exceptions() default "";

    /**
     *
     * @return @deprecated in javadoc
     */
    String deprecated() default "";
}
