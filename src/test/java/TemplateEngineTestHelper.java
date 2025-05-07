import java.util.Map;

public class TemplateEngineTestHelper {

    public static String renderInlineTemplate(String template, String entity, String basePackage, Map<String, String> extraPlaceholders) {
        String output = template
                .replace("{{PACKAGE}}", basePackage)
                .replace("{{ENTITY}}", entity)
                .replace("{{ENTITY_LOWER}}", Character.toLowerCase(entity.charAt(0)) + entity.substring(1));

        if (extraPlaceholders != null) {
            for (Map.Entry<String, String> entry : extraPlaceholders.entrySet()) {
                String key = entry.getKey().trim();
                if (!key.startsWith("{{")) key = "{{" + key;
                if (!key.endsWith("}}")) key = key + "}}";
                output = output.replace(key, entry.getValue().trim());
            }
        }

        return output;
    }
}
