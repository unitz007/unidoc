package org.unidoc.utils;

import com.github.javaparser.javadoc.JavadocBlockTag;

/**
 *
 * has methods assisting transformation of unidoc member values to java doc comments
 */
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
     * Used to confirm some tag names and convert from Uppercase to lowercase
     *
     * @param tag tag name to be adjusted
     * @return lowercase of tag name
     */
    public static String lowerCaseBlockTag(String tag) {
        return JavadocBlockTag.Type.valueOf(tag).name().toLowerCase();
    }
}
