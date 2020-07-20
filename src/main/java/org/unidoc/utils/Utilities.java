package org.unidoc.utils;

import com.github.javaparser.javadoc.JavadocBlockTag;

public class Utilities {

    public static String replace(String value) {
        return value.replace("\"", "");
    }

    public static String lowerCaseBlockTag(String tag) {
        return JavadocBlockTag.Type.valueOf(tag)
                .name()
                .toLowerCase();
    }
}
