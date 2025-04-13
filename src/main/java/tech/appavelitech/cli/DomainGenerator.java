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
package tech.appavelitech.cli;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DomainGenerator {

    public static void generate(String entity, String basePackage, String fieldInput) {
        List<Field> fields = parseFields(fieldInput);

        String fieldDeclarations = generateFieldDeclarations(fields);
        String gettersSetters = generateGettersSetters(fields);
        String toStringBody = generateToString(fields);

        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("{{FIELDS}}", fieldDeclarations);
        placeholders.put("{{GETTERS_SETTERS}}", gettersSetters);
        placeholders.put("{{TO_STRING_CONTENT}}", toStringBody);

        String template = TemplateEngine.render(
                "templates/DomainClass.template",
                entity,
                basePackage,
                placeholders
        );

        String outputDir = "generated/" + basePackage.replace('.', '/');
        new File(outputDir).mkdirs();

        writeToFile(outputDir + "/" + entity + ".java", template);
    }

    private static List<Field> parseFields(String input) {
        List<Field> fields = new ArrayList<>();
        String[] parts = input.split(",");
        for (String part : parts) {
            String[] tokens = part.trim().split(":");
            if (tokens.length == 2) {
                fields.add(new Field(tokens[0].trim(), tokens[1].trim()));
            }
        }
        return fields;
    }

    private static String generateFieldDeclarations(List<Field> fields) {
        StringBuilder sb = new StringBuilder();
        for (Field field : fields) {
            sb.append("    private ").append(field.type).append(" ").append(field.name).append(";\n");
        }
        return sb.toString();
    }

    private static String generateGettersSetters(List<Field> fields) {
        StringBuilder sb = new StringBuilder();
        for (Field field : fields) {
            String capitalized = capitalize(field.name);
            sb.append("    public ").append(field.type).append(" get").append(capitalized).append("() {\n")
                    .append("        return ").append(field.name).append(";\n")
                    .append("    }\n\n")
                    .append("    public void set").append(capitalized).append("(").append(field.type).append(" ").append(field.name).append(") {\n")
                    .append("        this.").append(field.name).append(" = ").append(field.name).append(";\n")
                    .append("    }\n\n");
        }
        return sb.toString();
    }

    private static String generateToString(List<Field> fields) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            sb.append("            \"").append(field.name).append("='\" + ").append(field.name).append(" + '\\''");
            if (i != fields.size() - 1) {
                sb.append(" +\n");
            }
        }
        return sb.toString();
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    private static void writeToFile(String path, String content) {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class Field {
        String name;
        String type;

        Field(String name, String type) {
            this.name = name;
            this.type = type;
        }
    }
}