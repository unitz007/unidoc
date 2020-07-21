package org.unidocTest;

import org.unidoc.ClassDoc;
import org.unidoc.ConstructorDoc;
import org.unidoc.FieldDoc;
import org.unidoc.MethodDoc;
import org.unidoc.ParameterDoc;

import java.io.FileNotFoundException;

@ClassDoc(
        value = "A class used for test" +
                "<p>This class is just for test<p>",
        author = "Dinneya Charles",
        version = "1.0.0"
)
public class TestClass {

    @FieldDoc(description = "test field")
    private String test = "global";

    @FieldDoc(description = "test field 2")
    private String test2;

    @ConstructorDoc("test constructor")
    public TestClass() {

    }


    @ConstructorDoc("test constructor with param")
    public TestClass(@FieldDoc(description = "test parameter") String test) {

    }

    @MethodDoc(returns = "test return")
    public String getTest(@FieldDoc(description = "dbfihs") int age)  {
        return test;
    }


    @MethodDoc(description = "this is a test method", exceptions = "enjo")
    public void setTest(@FieldDoc(description = "hbefihe") String test,
                        @FieldDoc(description = "jnfonojd") String rest,
                        @FieldDoc(description = "jfu") int restt) throws FileNotFoundException {

        this.test = test;
    }

}
