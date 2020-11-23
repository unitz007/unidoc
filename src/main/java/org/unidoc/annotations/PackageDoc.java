package org.unidoc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PACKAGE)
public @interface PackageDoc {

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
     * @return when package was created
     */
    String since() default "";
}
