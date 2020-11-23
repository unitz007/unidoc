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
     * @return description
     */
    String description() default "";

    /**
     *
     * @return name(s) of parameter(s), type and description
     */
    String[] params() default "";

    /**
     *
     * @return associated links and references
     */
    String see() default "";

    /**
     *
     * @return when constructor was created
     */
    String since() default "";

    /**
     *
     * @return description of the data to be serialized, including it's type and order
     */
    String serialData() default "";

    /**
     *
     * @return thrown exception(s) with description
     */
    String[] exceptions() default "";

    /**
     * recommends that a class shouldn't be used
     * @return why the constructor shouldn't be used and any reference
     */
    String deprecated() default "";
}
