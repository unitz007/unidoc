package org.unidoc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ClassDoc {

    /**
     *
     * @return author name
     */
    String[] author() default ""; // @author in javadoc

    /**
     *
     * @return description of class
     */
    String description() default ""; // description of class

    /**
     *
     * @return version
     */
    String version() default ""; // @version

    /**
     *
     * @return if it's deprecated
     */
    String deprecated() default ""; // @deprecated

}
