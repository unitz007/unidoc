package org.unidoc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface InterfaceDoc {

    /**
     *
     * @return description
     */
    String description() default "";

    /**
     *
     * @return author name(s)
     */
    String[] author() default "";

    /**
     *
     * @return version
     */
    String version() default "";

    /**
     *
     * @return associated links and references
     */
    String see() default "";

    /**
     *
     * @return when interface was created
     */
    String since() default "";

    /**
     *
     * @return whether the interface should be included in the serialized form page
     */
    String serial() default "";

    /**
     * declares that the interface shouldn't be documented
     *
     */
    String hidden() default "";


    /**
     * recommends that a interface shouldn't be used
     * @return why the interface shouldn't be used and any reference
     */
    String deprecated() default "";

}
