//package org.unidocTest.api;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.unidoc.core.ConstructorDocumentation;
//import org.unidocTest.TestClass;
//
//import java.lang.reflect.Constructor;
//import java.util.Arrays;
//import java.util.List;
//
//public class ConstructorDocumentationSpec {
//
//    private final ConstructorDocumentation doc = new ConstructorDocumentation();
//
//    @Test
//    void classIsAnnotated() {
//        List<Constructor<?>> constructors = Arrays.asList(TestClass.class.getDeclaredConstructors());
//        constructors.forEach(constructor -> {
//            Assertions.assertTrue(doc.isAnnotated(constructor));
//        });
//    }
//
//}