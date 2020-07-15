package org.unidoc.api;

import org.unidoc.core.ParameterDoc;

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
