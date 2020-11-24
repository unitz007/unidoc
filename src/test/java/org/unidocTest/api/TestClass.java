package org.unidocTest.api;

import org.unidoc.annotations.*;

import java.io.FileNotFoundException;
import java.io.IOException;

@ClassDoc(
        description = "<h1>A class used for test</h1>" +
                "<p>" +
                "This class is just for test. blah blah blah blah blah blah blah" +
                "<p>" +
                "<pre>" +
                "public class JavadocTest {" +
                "   &#64;SuppressWarnings" +
                "   public List&#60;String&#62; generics(){" +
                "       // '@', '<' and '>'  have to be escaped with HTML codes" +
                "   }" +
                "}" +
                "</pre>" +
                "hhjlkhk      " +
                "hdsksjsha " +
                "{@link #getTest(int) getTest}" +
                "hfghdsd" +
                " hmmmm",
        author = {"Dinneya Charles", "Emanuel Ninja"},
        version = "1.0.0",
        see = "<a href=\"URL#value\">label</a>",
        since = "2020-11-06",
        serial = "include",
        deprecated = "no"
)
public class TestClass {

    @FieldDoc(description = "test field",
            see = "<a href=\"URL#value\">label</a>",
            since = "2020-11-07",
            serial = "djdjknjdghhhgh",
            serialField = {"test", "String", "sddsjj " +
                    "hfkdks"},
            deprecated = "no"
            )
    private String test = "global";

    @FieldDoc(description = "test field 2")
    private String test2;

    @ConstructorDoc(description = "Testclass constructor")
    public TestClass() {
    }

    @ConstructorDoc(
                    description = "Testclass constructor with one parameter",
                    see = "<a href=\"URL#value\">label</a>",
                    since = "2020-11-07",
                    serialData = "...",
                    exceptions = {"this file... " +
                            "kjdfdllkkd"},
                    deprecated = "no"
    )
    public TestClass(@ParamDoc(description = "test parameter") String test) throws FileNotFoundException {
    }

    @MethodDoc(returns = "test return " +
            "is ...")
    public String getTest(@ParamDoc(description = "dbfihs " +
                                    "bkklk...") int age)  {
        return test;
    }


    @MethodDoc(
                description = "this is a test method " +
                        "and...",
                exceptions = {"enjo " +
                    "hjdjdkjjkdj",
                    "piss"},
                see = "<a href=\"URL#value\">label</a>",
                since = "2020-11-07",
                serialData = "...",
                deprecated = "no"
            )
    public void setTest(@ParamDoc(description = "hbefihe") String test,
                        @ParamDoc(description = "jnfonojd") String rest,
                        @ParamDoc(description = "jfu") int restt) throws FileNotFoundException, IOException {

        this.test = test;
    }

}


