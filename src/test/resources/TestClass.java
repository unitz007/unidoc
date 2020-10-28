package org.unidocTest;

import org.unidoc.ClassDoc;
import org.unidoc.ConstructorDoc;
import org.unidoc.FieldDoc;
import org.unidoc.MethodDoc;
import java.io.FileNotFoundException;

/**
 * A class used for test  + <p>This class is just for test<p>.
 *
 * @author Dinneya Charles
 * @author Emanuel Ninja
 * @version 1.0.0
 */
public class TestClass {

    /**
     * test field
     */
    private String test = "global";

    /**
     * test field 2
     */
    private String test2;

    /**
     * Testclass constructor
     */
    public TestClass() {
    }

    /**
     * Testclass constructor with one parameter
     *
     * @param test test parameter
     */
    public TestClass(String test) {
    }

    /**
     * getTest.
     *
     * @author Dinneya Charles
     * @version 1.0.0
     * @param age dbfihs
     * @return test return
     */
    public String getTest(int age) {
        return test;
    }

    /**
     * this is a test method.
     *
     * @author Dinneya David
     * @version 1.0.0
     * @param test hbefihe
     * @param rest jnfonojd
     * @param restt jfu
     * @throws FileNotFoundException enjo
     */
    public void setTest(String test, String rest, int restt) throws FileNotFoundException {
        this.test = test;
    }
}
