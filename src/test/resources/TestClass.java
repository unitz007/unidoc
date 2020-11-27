package org.unidocTest.api;

import org.unidoc.annotations.*;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * <h1>A class used for test</h1>
 * <p>
 * This class is just for test. blah blah blah blah blah blah blah
 * <p>
 * <pre>
 * public class JavadocTest {
 *    &#64;SuppressWarnings
 *    public List&#60;String&#62; generics(){
 *        // '@', '<' and '>'  have to be escaped with HTML codes
 *    }
 * }
 * </pre>
 * hhjlkhk
 * hdsksjsha
 * {@link #getTest(int) getTest}
 * hfghdsd
 *  hmmmm
 *
 * @author Dinneya Charles
 * @author Emanuel Ninja
 * @version 1.0.0
 * @param T ...
 * @see <a href=\URL#value\>label</a>
 * @since 2020-11-06
 * @hidden
 * @deprecated no
 */
public class TestClass<T> {

    /**
     * test field
     *
     * @see <a href=\URL#value\>label</a>
     * @since 2020-11-07
     * @serial djdjknjdghhhgh
     * @serialField test String sddsjj
     * hfkdks.
     * @deprecated no
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
     * @throws FileNotFoundException this file...
     * kjdfdllkkd.
     * @see <a href=\URL#value\>label</a>
     * @since 2020-11-07
     * @deprecated no
     */
    public TestClass(String test) throws FileNotFoundException {
    }

    /**
     * getTest.
     *
     * @param age dbfihs
     * bkklk...
     * @return test return
     * is ...
     */
    public String getTest(int age) {
        return test;
    }

    /**
     * this is a test method
     * and....
     *
     * @param test hbefihe
     * @param rest jnfonojd
     * @param restt jfu
     * @throws FileNotFoundException enjo
     * hjdjdkjjkdj.
     * @throws IOException piss.
     * @see <a href=\URL#value\>label</a>
     * @since 2020-11-07
     * @serialData ...
     * @deprecated no
     */
    public void setTest(String test, String rest, int restt) throws FileNotFoundException, IOException {
        this.test = test;
    }
}
