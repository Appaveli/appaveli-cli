package io.github.appaveli.cli;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuthUtilsGenerator {

    public static void generate(String entity, String utilPackage, String domainPackage) {
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("PACKAGE", utilPackage);
        placeholders.put("DOMAIN_IMPORT", domainPackage + "." + entity);
        placeholders.put("ENTITY", entity);

        String outputDir = "generated/" + utilPackage.replace('.', '/');
        new File(outputDir).mkdirs();

        renderToFile("templates/SessionUtils.java.template", outputDir + "/SessionUtils.java", entity, utilPackage, placeholders);
        renderToFile("templates/PasswordUtils.java.template", outputDir + "/PasswordUtils.java", entity, utilPackage, placeholders);
    }

    private static void renderToFile(String templatePath, String outputPath, String entity, String basePackage, Map<String, String> placeholders) {
        String content = TemplateEngine.render(templatePath, entity, basePackage, placeholders);
        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
