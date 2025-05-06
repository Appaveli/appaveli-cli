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

public class DaoGenerator {
    public static void generate(String entity, String basePackage) {
        String interfaceContent = TemplateEngine.render("templates/DaoInterface.template", entity, basePackage);
        String implContent = TemplateEngine.render("templates/DaoImpl.template", entity, basePackage);

        String outputDir = "generated/" + basePackage.replace('.', '/');
        new File(outputDir).mkdirs();

        writeToFile(outputDir + "/" + entity + "Dao.java", interfaceContent);
        writeToFile(outputDir + "/" + entity + "DaoImpl.java", implContent);
    }

    private static void writeToFile(String path, String content) {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
