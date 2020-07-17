package org.unidoc.core;

import org.unidoc.ParameterDoc;

import java.lang.reflect.Parameter;

/**
 * @author <a href=mailto:dinneyacharles5@gmail.com>Dinneya Charles</a>
 * @version 1.0.0
 */
public class ParameterDocumentation {

    /**
     * checks if method parameter(s) is annotated with @ParameterDoc.
     * @return true if its annotated
     */
    public boolean isAnnotated(Parameter parameter) {
        return parameter.isAnnotationPresent(ParameterDoc.class);
    }

}
