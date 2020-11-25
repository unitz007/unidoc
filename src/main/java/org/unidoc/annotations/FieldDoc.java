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
     * @return description
     */
    String description() default "";

    /**
     *
     * @return associated links and references
     */
    String see() default "";

    /**
     *
     * @return when field was created
     */
    String since() default "";

    /**
     *
     * @return description of the field, including acceptable serializable values
     */
    String serial() default "";

    /**
     *
     * @return field name, type and description of an ObjectStreamField
     */
    String[] serialField() default "";

    /**
     * declares that the field shouldn't be documented
     *
     */
    String hidden() default "";

    /**
     * recommends that a field shouldn't be used
     * @return why the field shouldn't be used and any reference
     */
    String deprecated() default "";


}
