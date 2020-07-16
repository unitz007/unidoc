package org.unidoc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MethodDoc {

    /**
     *
     * @return description of method
     */
    String value() default ""; // description

    /**
     *
     * @return description of return value
     */
    String returns() default "";

}
