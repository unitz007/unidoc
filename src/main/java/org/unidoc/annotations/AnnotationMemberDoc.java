package org.unidoc.annotations;

public @interface AnnotationMemberDoc {

    /**
     *
     * @return description
     */
    String description() default "";

    /**
     *
     * @return version
     */
    String version() default "";

    /**
     *
     * @return info about return type
     */
    String returns() default "";

    /**
     *
     * @return associated links and references
     */
    String see() default "";

    /**
     *
     * @return when annotation member was created
     */
    String since() default "";

    /**
     * declares that the annotation member shouldn't be documented
     *
     */
    String hidden() default "";

    /**
     * recommends that a annotation member shouldn't be used
     * @return why the annotation member shouldn't be used and any reference
     */
    String deprecated() default "";
}
