package org.unidoc.utils;

public class Utilities {
    public static String getFile(Class<?> clazz) {
        String projectDir = System.getProperty("user.dir") + "/src/main/java/";
        String pkg = clazz.getCanonicalName().replace('.', '/');
        return projectDir + pkg + ".java";

    }

//    public static String buildEnv() {
//        return "noy yet";
//    }

}
