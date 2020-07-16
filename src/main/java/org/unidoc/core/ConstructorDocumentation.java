package org.unidoc.core;

import org.unidoc.ConstructorDoc;

import java.lang.reflect.Constructor;

public class ConstructorDocumentation {
    /**
     * checks if class has @ConstructorDoc annotations.
     * @param constructor constructor
     * @return true if its annotated
     */
    public boolean isAnnotated(Constructor<?> constructor) {
       return constructor.isAnnotationPresent(ConstructorDoc.class);
    }

}
