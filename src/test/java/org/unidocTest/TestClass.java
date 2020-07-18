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

    @FieldDoc("test field")
    private String test = "global";

    @ConstructorDoc("test constructor")
    public TestClass() {

    }


    @ConstructorDoc("test constructor with param")
    public TestClass(@ParameterDoc("test parameter") String test) {

    }

    @MethodDoc(description = "eihiufhf", returns = "hfihfuh")
    public String getTest() {
        return test;
    }


    /**
     *
     * @param test
     * @return
     * @throws FileNotFoundException
     */
    @MethodDoc(description = "beqfuihuuddu", returns = "jnhidd", exceptions = "bfdyd", params = "jnjdj")
    public String setTest(String test) throws FileNotFoundException {
        this.test = test;
        return test;
    }

}
