package org.unidoc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * for documenting a constructor declaration
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.CONSTRUCTOR)
public @interface ConstructorDoc {

    /**
     * @return description
     */
    String description() default "";

    /**
     * @return version
     */
    String version() default "";

    /**
     * @return associated links and references
     */
    String see() default "";

    /**
     * @return when constructor was created
     */
    String since() default "";

    /**
     * @return thrown exception(s) with description
     */
    String[] exceptions() default "";

    /**
     * declares that the constructor shouldn't be documented
     */
    String hidden() default "";

    /**
     * recommends that a constructor shouldn't be used
     * @return why the constructor shouldn't be used and any reference
     */
    String deprecated() default "";
}
