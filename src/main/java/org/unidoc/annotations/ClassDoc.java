package org.unidoc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ClassDoc {

    /**
     *
     * @return description of class
     */
    String description() default ""; // description of class

    /**
     *
     * @return @author name
     */
    String[] author() default ""; // @author in javadoc

    /**
     *
     * @return @version
     */
    String version() default ""; // @version

    /**
     *
     * @return @see
     */
    String see() default ""; //@see

    /**
     *
     * @return @since
     */
    String since() default ""; //@since

    /**
     *
     * @return @serial
     */
    String serial() default "";

    /**
     *
     * @return @deprecated
     */
    String deprecated() default ""; // @deprecated

}
