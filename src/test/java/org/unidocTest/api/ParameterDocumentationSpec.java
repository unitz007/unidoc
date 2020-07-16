package org.unidocTest.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.unidoc.core.ParameterDocumentation;
import org.unidocTest.TestClass;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

public class ParameterDocumentationSpec {

    private final ParameterDocumentation parameterDocumentation = new ParameterDocumentation();

    @Test
    void isParameterDocAnnotated() {
        List<Method> methods = Arrays.asList(TestClass.class.getDeclaredMethods());
        methods.forEach(method -> {
            List<Parameter> parameters = Arrays.asList(method.getParameters());
            parameters.forEach(parameter -> {
                Assertions.assertTrue(parameterDocumentation.isAnnotated(parameter));
            });
        });
    }

}
