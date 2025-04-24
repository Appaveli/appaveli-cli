package tech.appavelitech.cli;

import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class SqlGenerator {

    public static void generate(String entity, String fieldInput) {
        String tableName = entity.toLowerCase();
        String[] fields = fieldInput.split(",");
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE ").append(tableName).append(" (\n");

        for (int i = 0; i < fields.length; i++) {
            String[] parts = fields[i].trim().split(":");
            if (parts.length != 2) continue;

            String name = parts[0].trim();
            String type = mapJavaTypeToSql(parts[1].trim());
            sql.append("    ").append(name).append(" ").append(type);

            if (name.equalsIgnoreCase("id")) {
                sql.append(" PRIMARY KEY");
            }

            if (i < fields.length - 1) {
                sql.append(",");
            }
            sql.append("\n");
        }

        sql.append(");\n");

        String outputDir = "generated/sql";
        new File(outputDir).mkdirs();
        String outputPath = outputDir + "/" + entity + ".sql";

        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write(sql.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String mapJavaTypeToSql(String javaType) {
        return switch (javaType.toLowerCase()) {
            case "int", "integer" -> "INT";
            case "long" -> "BIGINT";
            case "string" -> "VARCHAR(255)";
            case "boolean" -> "BOOLEAN";
            case "double" -> "DOUBLE";
            case "date" -> "DATE";
            default -> "VARCHAR(255)";
        };
    }
}
