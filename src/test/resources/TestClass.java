package org.unidocTest;

import org.unidoc.ClassDoc;
import org.unidoc.ConstructorDoc;
import org.unidoc.FieldDoc;
import org.unidoc.MethodDoc;
import java.io.FileNotFoundException;

@ClassDoc(description = "A class used for test" + "<p>This class is just for test<p>", author = "Dinneya Charles", version = "1.0.0")
public class TestClass {

    /**
     * test field
     */
    @FieldDoc(description = "test field")
    private String test = "global";

    /**
     * test field 2
     */
    @FieldDoc(description = "test field 2")
    private String test2;

    @ConstructorDoc("test constructor")
    public TestClass() {
    }

    @ConstructorDoc("test constructor with param")
    public TestClass(@FieldDoc(description = "test parameter") String test) {
    }

    /**
     * getTest.
     *
     * @param age dbfihs
     * @return  test return
     */
    @MethodDoc(returns = "test return")
    public String getTest(@FieldDoc(description = "dbfihs") int age) {
        return test;
    }

    /**
     * this is a test method.
     *
     * @param test hbefihe
     * @param rest jnfonojd
     * @param restt jfu
     * @throws FileNotFoundException enjo
     */
    @MethodDoc(description = "this is a test method", exceptions = "enjo")
    public void setTest(@FieldDoc(description = "hbefihe") String test, @FieldDoc(description = "jnfonojd") String rest, @FieldDoc(description = "jfu") int restt) throws FileNotFoundException {
        this.test = test;
    }
}
