package tech.appavelitech.cli;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ServiceGenerator {
    public static void generate(String entity, String basePackage) {
        String interfaceContent = TemplateEngine.render("templates/ServiceInterface.template", entity, basePackage);
        String implContent = TemplateEngine.render("templates/ServiceImpl.template", entity, basePackage);

        String outputDir = "generated/" + basePackage.replace('.', '/');
        new File(outputDir).mkdirs();

        writeToFile(outputDir + "/" + entity + "Service.java", interfaceContent);
        writeToFile(outputDir + "/" + entity + "ServiceImpl.java", implContent);
    }

    private static void writeToFile(String path, String content) {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
