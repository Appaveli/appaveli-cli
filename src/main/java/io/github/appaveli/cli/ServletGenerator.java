package io.github.appaveli.cli;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ServletGenerator {

    public static void generate(String entity, String basePackage) {
        String templatePath = "templates/Servlet.template";
        String content = TemplateEngine.render(templatePath, entity, basePackage);

        String outputDir = "generated/" + basePackage.replace('.', '/');
        new File(outputDir).mkdirs();

        String outputPath = outputDir + "/" + entity + "Servlet.java";
        writeToFile(outputPath, content);

        System.out.println("Servlet generated at: " + outputPath);
    }

    private static void writeToFile(String path, String content) {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}