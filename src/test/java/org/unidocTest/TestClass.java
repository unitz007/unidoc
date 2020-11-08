package org.unidocTest;

import org.unidoc.annotations.*;

import java.io.FileNotFoundException;
import java.io.IOException;

@ClassDoc(
        description = "<h1>A class used for test</h1> " +
                "<p>" +
                "This class is just for test. blah blah blah blah blah blah blah" +
                "<p> \n" +
                "<pre> \n" +
                "public class JavadocTest {\n" +
                "   &#64;SuppressWarnings \n" +
                "   public List&#60;String&#62; generics(){ \n" +
                "       // '@', '<' and '>'  have to be escaped with HTML codes \n" +
                "   } \n" +
                "} \n" +
                "</pre> \n" +
                "hhjlkhk " +
                "{@link #getTest(int) getTest} \n" +
                " hfghdsd",
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
            serial = "djdjknjd",
            serialField = {"test", "String", "sddsjj"},
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
                    returns = "an instance",
                    see = "<a href=\"URL#value\">label</a>",
                    since = "2020-11-07",
                    serialData = "...",
                    exceptions = {"this file..."},
                    deprecated = "no"
    )
    public TestClass(@ParamDoc(description = "test parameter") String test) throws FileNotFoundException {
    }

    @MethodDoc(returns = "test return")
    public String getTest(@ParamDoc(description = "dbfihs") int age)  {
        return test;
    }


    @MethodDoc(
                description = "this is a test method",
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

