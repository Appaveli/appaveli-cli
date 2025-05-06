package io.github.appaveli.cli;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuthGenerator {

    public static void generate(String entity, String basePackage, String daoPackage, String domainPackage) {
        String entityLower = Character.toLowerCase(entity.charAt(0)) + entity.substring(1);

        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("ENTITY", entity);
        placeholders.put("ENTITY_LOWER", entityLower);
        placeholders.put("DAO_IMPORT", daoPackage + "." + entity + "Dao");
        placeholders.put("DAO_IMPL_IMPORT", daoPackage + "." + entity + "DaoImpl");
        placeholders.put("DOMAIN_IMPORT", domainPackage + "." + entity);

        // Generate LoginServlet
        generateFile("templates/LoginServlet.template", basePackage, entity, "LoginServlet.java", placeholders, "generated/" + basePackage.replace('.', '/'));

        // Generate JSPs
        String jspBase = "generated/views/auth";
        generateFile("templates/login.jsp.template", basePackage, entity, "login.jsp", placeholders, jspBase);
        generateFile("templates/dashboard.jsp.template", basePackage, entity, "dashboard.jsp", placeholders, jspBase);
        generateFile("templates/logout.jsp.template", basePackage, entity, "logout.jsp", placeholders, jspBase);
    }

    private static void generateFile(String templatePath, String basePackage, String entity, String outputName, Map<String, String> placeholders, String outputDir) {
        String content = TemplateEngine.render(templatePath, entity, basePackage, placeholders);
        new File(outputDir).mkdirs();
        try (FileWriter writer = new FileWriter(outputDir + "/" + outputName)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
