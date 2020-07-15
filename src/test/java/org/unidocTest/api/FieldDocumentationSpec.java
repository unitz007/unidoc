package org.unidocTest.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.unidoc.api.FieldDocumentation;
import org.unidocTest.TestClass;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class FieldDocumentationSpec {

    FieldDocumentation fieldDocumentation = new FieldDocumentation();

    @Test
    @DisplayName("should check if @FieldDoc is present")
    void hasFieldDocAnnotation() {
        List<Field> fields = Arrays.asList(TestClass.class.getDeclaredFields());
        fields.forEach(field -> {
            Assertions.assertTrue(fieldDocumentation.isAnnotated(field));
        });
    }


}
