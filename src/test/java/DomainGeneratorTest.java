/*
 * MIT License
 *
 * Copyright (c) 2024 AppaveliTech Solutions, LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 */
import org.junit.jupiter.api.Test;
import tech.appavelitech.cli.DomainGenerator;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class DomainGeneratorTest {

    @Test
    void test_domain_class_generation() throws Exception {
        Path templateDir = Files.createTempDirectory("templates");
        Path domainTemplate = templateDir.resolve("DomainClass.template");

        String template = """
package {{PACKAGE}};

public class {{ENTITY}} {
{{FIELDS}}

{{GETTERS_SETTERS}}

@Override
public String toString() {
    return "{{ENTITY}}{" +
{{TO_STRING_CONTENT}}        '}';
}
}
""";
        Files.writeString(domainTemplate, template);
        System.setProperty("user.dir", templateDir.getParent().toString());

        DomainGenerator.generate("User", "com.example", "id:int,name:String");

        File outputFile = new File("generated/com/example/User.java");
        assertTrue(outputFile.exists(), "Domain file should be created");
        String content = Files.readString(outputFile.toPath());
        assertTrue(content.contains("private int id;"));
        assertTrue(content.contains("private String name;"));
    }
}
