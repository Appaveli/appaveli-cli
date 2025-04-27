package tech.appavelitech.cli;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JspGenerator {

    public static void generate(String entity, String viewsInput) {
        String entityLower = entity.toLowerCase();
        String[] views = viewsInput.split(",");

        String basePath = "generated/views/" + entityLower;
        new File(basePath).mkdirs();

        for (String view : views) {
            String viewName = view.trim();
            String templatePath = "templates/" + viewName + ".jsp.template";

            String rendered = TemplateEngine.render(templatePath, entity, "")
                    .replace("{{ENTITY_LOWER}}", entityLower);  // Ensure lower entity injection

            String outputPath = basePath + "/" + viewName + ".jsp";
            writeToFile(outputPath, rendered);
        }

        System.out.println("JSP views generated for: " + entity + " → " + viewsInput);
    }

    private static void writeToFile(String path, String content) {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
