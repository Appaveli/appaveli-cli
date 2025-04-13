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
import tech.appavelitech.cli.TemplateEngine;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TemplateEngineTest {

    @Test
    void test_basic_template_rendering() throws Exception {
        String template = "package {{PACKAGE}};\npublic class {{ENTITY}} { }";
        Path tempFile = Files.createTempFile("template", ".tmp");
        Files.writeString(tempFile, template);

        String result = TemplateEngine.render(tempFile.toString(), "User", "com.example");

        assertTrue(result.contains("package com.example;"));
        assertTrue(result.contains("public class User"));
    }

    @Test
    void test_template_with_extra_placeholders() throws Exception {
        String template = "class {{ENTITY}} { {{EXTRA}} }";
        Path tempFile = Files.createTempFile("template", ".tmp");
        Files.writeString(tempFile, template);

        String result = TemplateEngine.render(tempFile.toString(), "User", "ignored", Map.of("{{EXTRA}}", "int id;"));

        assertTrue(result.contains("class User"));
        assertTrue(result.contains("int id;"));
    }
}
