package org.unidoc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.MODULE)
public @interface ModuleDoc {

    /**
     *
     * @return description
     */
    String description() default "";

    /**
     *
     * @return author(s)
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
     * @return when module was created
     */
    String since() default "";

    /**
     *
     * @return field name, type and description of an ObjectStreamField
     */
    String[] serialField() default "";

    /**
     *
     * @return service type and description of service provided
     */
    String[] provides() default "";

    /**
     *
     * @return service type and description of service
     */
    String[] uses() default "";

    /**
     * recommends that a module shouldn't be used
     * @return why the module shouldn't be used and any reference
     */
    String deprecated() default "";
}
