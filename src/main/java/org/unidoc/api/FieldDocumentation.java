package org.unidoc.api;

import org.unidoc.core.FieldDoc;

import java.lang.reflect.Field;

public class FieldDocumentation {

    /**
     *
     * @param field field to check
     * @return true if field is annotated
     */
    public boolean isAnnotated(Field field) {
        return field.isAnnotationPresent(FieldDoc.class);
    }
}
