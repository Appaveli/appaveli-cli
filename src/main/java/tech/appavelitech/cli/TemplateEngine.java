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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class TemplateEngine {

    public static String render(String templatePath, String entity, String basePackage) {
        return render(templatePath, entity, basePackage, null);
    }

    public static String render(String templatePath, String entity, String basePackage, Map<String, String> extraPlaceholders) {
        try {
            String template = Files.readString(Path.of(templatePath));

            template = template
                    .replace("{{PACKAGE}}", basePackage)
                    .replace("{{ENTITY}}", entity)
                    .replace("{{ENTITY_LOWER}}", Character.toLowerCase(entity.charAt(0)) + entity.substring(1));

            if (extraPlaceholders != null) {
                for (Map.Entry<String, String> entry : extraPlaceholders.entrySet()) {
                    template = template.replace(entry.getKey(), entry.getValue());
                }
            }

            return template;

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}