package io.github.appaveli.cli;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class AuthServletGenerator {

    public static void generate(String entity, String servletPackage, String daoPackage, String domainPackage, String utilPackage) {
        String entityLower = Character.toLowerCase(entity.charAt(0)) + entity.substring(1);

        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("ENTITY", entity);
        placeholders.put("ENTITY_LOWER", entityLower);
        placeholders.put("PACKAGE", servletPackage);
        placeholders.put("DAO_IMPORT", daoPackage + "." + entity + "Dao");
        placeholders.put("DAO_IMPL_IMPORT", daoPackage + "." + entity + "DaoImpl");
        placeholders.put("DOMAIN_IMPORT", domainPackage + "." + entity);
        placeholders.put("UTIL_IMPORT", utilPackage);

        String outputDir = "generated/" + servletPackage.replace('.', '/');
        new File(outputDir).mkdirs();

        renderToFile("templates/LoginServlet.template", outputDir + "/LoginServlet.java", entity, servletPackage, placeholders);
        renderToFile("templates/LogoutServlet.template", outputDir + "/LogoutServlet.java", entity, servletPackage, placeholders);

        injectIntoMain(entity);
    }

    private static void renderToFile(String templatePath, String outputPath, String entity, String basePackage, Map<String, String> placeholders) {
        String content = TemplateEngine.render(templatePath, entity, basePackage, placeholders);
        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void injectIntoMain(String entity) {
        String mainPath = "generated/Main.java";
        File mainFile = new File(mainPath);
        if (!mainFile.exists()) return;

        try {
            List<String> lines = Files.readAllLines(mainFile.toPath());
            List<String> modified = new ArrayList<>();
            String insert = String.format("        handler.addServlet(%s.class, \"%s\");", entity + "Servlet", "/" + entity.toLowerCase());
            String login = "        handler.addServlet(LoginServlet.class, \"/login\");";
            String logout = "        handler.addServlet(LogoutServlet.class, \"/logout\");";

            boolean injected = false;
            for (String line : lines) {
                modified.add(line);
                if (!injected && line.contains("// Register servlets here")) {
                    modified.add(login);
                    modified.add(logout);
                    injected = true;
                }
            }

            Files.write(mainFile.toPath(), modified);
            System.out.println("Auth routes injected into Main.java");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
