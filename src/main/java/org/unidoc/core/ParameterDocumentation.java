package org.unidoc.core;

import org.unidoc.ParameterDoc;

import java.lang.reflect.Parameter;

public class ParameterDocumentation {

    /**
     *
     * @return true
     */
    public boolean isAnnotated(Parameter parameter) {
        return parameter.isAnnotationPresent(ParameterDoc.class);
    }

}
