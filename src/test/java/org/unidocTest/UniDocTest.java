package org.unidocTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.unidoc.ClassDoc;
import org.unidoc.FieldDoc;

import java.io.*;
import java.nio.file.FileSystems;
import java.util.Scanner;

@ClassDoc(author = "charles", value = "cgadsf", version = "1.0.0")
public class UniDocTest {

    @FieldDoc(description = "test field")
    private int testField;

    @Test
    @DisplayName("Checks if @ClassDoc is not null")
    void testClassDocAnnotationIsNotNull() {
        Assertions.assertEquals(2, ClassDoc.class.getAnnotations().length);
        Class<?> clazz = this.getClass();
        Assertions.assertNotNull(clazz.getAnnotation(ClassDoc.class));
    }

    @Test
    void testLineNumber() throws FileNotFoundException {
        String pkg = this.getClass().getPackage().getName();
        String className;
        String dir = System.getProperty("user.dir") + ".java";
        File fileReader = new File(dir);
        Scanner scanner = new Scanner(fileReader);
        int lineNumber = 1;
        while (scanner.hasNextLine()) {
                if (scanner.nextLine().startsWith("public class")) {
                    break;
                } else {
                    lineNumber++;

                }

        }
        Assertions.assertEquals(3, lineNumber);
        System.out.println(FileSystems.getDefault().getPath("").toAbsolutePath());

    }


}
