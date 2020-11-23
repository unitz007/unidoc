package org.unidoc.utils;

import com.github.javaparser.javadoc.JavadocBlockTag;

public class Utilities {

    /**
     * Removes all " from member value
     *
     * @param value member value to be adjusted
     * @return adjusted member value
     */
    public static String replace(String value) {
        return value.replace("\"", "").trim();
    }

    /**
     *
     * converts Uppercase tag names to lowercase
     *
     * @param tag tag name to be adjusted
     * @return lowercase of tag name
     *
     */
    public static String lowerCaseBlockTag(String tag) {
        return JavadocBlockTag.Type.valueOf(tag)
                .name()
                .toLowerCase();
    }
}
