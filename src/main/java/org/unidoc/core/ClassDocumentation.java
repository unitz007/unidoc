package org.unidoc.core;

import org.unidoc.ClassDoc;

public class ClassDocumentation {

//    public void document(Class<?> cls) {
//        cls.getDeclaredAnnotationsByType(ClassDoc.class);
//    }

    /**
     * checks if class is annotated with @ClassDoc.
     * @param cls class
     * @return boolean
     */
    public boolean isAnnotated(Class<?> cls) {
        return cls.isAnnotationPresent(ClassDoc.class); // true if cls has @ClassDoc
    }
}
