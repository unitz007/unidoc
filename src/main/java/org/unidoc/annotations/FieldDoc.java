package org.unidoc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldDoc {

    /**
     *
     * @return description of field
     */
    String description() default "";

    /**
     *
     * @return @see of field
     */
    String see() default "";

    /**
     *
     * @return @since
     */
    String since() default "";

    /**
     *
     * @return @serial
     */
    String serial() default "";

    /**
     *
     * @return @serialField
     */
    String[] serialField() default "";

    /**
     *
     * @return @deprecated
     */
    String deprecated() default "";


}
