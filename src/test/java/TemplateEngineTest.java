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

import io.github.appaveli.cli.TemplateEngine;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TemplateEngineTest {

    @Test
    void test_render_dao_interface_template() {
        String result = TemplateEngine.render("templates/DaoInterface.template", "User", "com.example.dao");

        assertTrue(result.contains("package com.example.dao"));
        assertTrue(result.contains("interface UserDao"));
    }

    @Test
    void test_render_with_additional_placeholders() {
        Map<String, String> extra = Map.of(
                "EXTRA", "int id;",
                "ENTITY_LOWER", "user"
        );

        String raw = "public class {{ENTITY}} { {{EXTRA}} public String get{{ENTITY}}Name() {} }";
        String expected = "public class User { int id; public String getUserName() {} }";

        String result = TemplateEngineTestHelper.renderInlineTemplate(raw, "User", "ignored", extra);

        assertTrue(result.contains("User"));
        assertTrue(result.contains("int id;"));
        assertTrue(result.contains("getUserName"));
    }
}

