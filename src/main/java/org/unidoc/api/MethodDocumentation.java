package org.unidoc.api;

import org.unidoc.core.MethodDoc;

import java.lang.reflect.Method;

public class MethodDocumentation {

    /**
     *
     * @param method method
     * @return true if it's annotated
     */
    public boolean isAnnotated(Method method) {
        return method.isAnnotationPresent(MethodDoc.class);
    }

}
