package org.unidocTest;

import org.unidoc.ClassDoc;
import org.unidoc.ConstructorDoc;
import org.unidoc.FieldDoc;
import org.unidoc.MethodDoc;
import org.unidoc.ParameterDoc;

@ClassDoc("Test class")
public class TestClass {

    @FieldDoc("test field")
    private String test;

    @ConstructorDoc("test constructor")
    public TestClass() { }

    @ConstructorDoc("test constructor with param")
    public TestClass(@ParameterDoc("test parameter") String test) {
        this.test = test;
    }

    @MethodDoc(value = "test method")
    public String getTest() {
        return test;
    }
}
