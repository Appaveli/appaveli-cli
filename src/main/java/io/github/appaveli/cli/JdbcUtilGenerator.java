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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class JdbcUtilGenerator {
    public static void generate(String basePackage, String dbName, String dbUser, String dbPassword) {
        String content = TemplateEngine.render(
                "templates/JdbcUtils.template",
                Map.of(
                        "PACKAGE", basePackage,
                        "DB_NAME", dbName,
                        "DB_USER", dbUser,
                        "DB_PASSWORD", dbPassword
                )
        );

        String outputDir = "generated/" + basePackage.replace('.', '/');
        new File(outputDir).mkdirs();

        writeToFile(outputDir + "/DatabaseConnection.java", content);
    }

    private static void writeToFile(String path, String content) {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
