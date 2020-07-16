package org.unidoc.core;

import org.unidoc.MethodDoc;

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
