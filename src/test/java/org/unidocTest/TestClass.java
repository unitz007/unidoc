package org.unidocTest;

import org.unidoc.core.ClassDoc;
import org.unidoc.core.ConstructorDoc;
import org.unidoc.core.FieldDoc;
import org.unidoc.core.MethodDoc;
import org.unidoc.core.ParameterDoc;

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
