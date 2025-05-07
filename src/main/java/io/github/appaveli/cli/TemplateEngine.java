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
package io.github.appaveli.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

public class TemplateEngine {

    public static String render(String resourcePath, String entity, String basePackage) {
        return render(resourcePath, entity, basePackage, null);
    }

    public static String render(String resourcePath, String entity, String basePackage, Map<String, String> extraPlaceholders) {
        String template = readTemplateFromResources(resourcePath);
        if (template.isEmpty()) return "";

        template = template
                .replace("{{PACKAGE}}", basePackage)
                .replace("{{ENTITY}}", entity)
                .replace("{{ENTITY_LOWER}}", Character.toLowerCase(entity.charAt(0)) + entity.substring(1));

        if (extraPlaceholders != null) {
            template = handlePlaceholders(extraPlaceholders, template);
        }

        return template;
    }

    public static String render(String resourcePath, Map<String, String> placeholders) {
        String template = readTemplateFromResources(resourcePath);
        if (template.isEmpty()) return "";

        return handlePlaceholders(placeholders, template);
    }

    private static String readTemplateFromResources(String resourcePath) {
        try (InputStream in = TemplateEngine.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (in == null) {
                System.err.println("Template not found: " + resourcePath);
                return "";
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                return reader.lines().collect(Collectors.joining("\n"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String handlePlaceholders(Map<String, String> extraPlaceholders, String template) {
        for (Map.Entry<String, String> entry : extraPlaceholders.entrySet()) {
            String key = entry.getKey().trim();
            if (!key.startsWith("{{")) key = "{{" + key;
            if (!key.endsWith("}}")) key = key + "}}";
            template = template.replace(key, entry.getValue().trim());
        }
        return template;
    }
}