package org.unidoc.core;

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
    String author() default ""; // @author in javadoc

    /**
     *
     * @return description of class
     */
    String value() default ""; // description of class

    /**
     *
     * @return version
     */
    String version() default ""; // @version

    /**
     *
     * @return true if it's deprecated
     */
    boolean deprecated() default false; // @deprecated

}
