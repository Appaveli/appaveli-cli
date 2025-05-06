package io.github.appaveli.cli;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.net.URL;
import java.net.URISyntaxException;

public class InitProjectGenerator {

    public static void generate(String projectName, String basePackage) {
        String outputDir = "generated/" + projectName;
        String resourceBase = "templates/init-project";

        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("PROJECT_NAME", projectName);
        placeholders.put("PACKAGE", basePackage);

        try {
            List<String> files = listResourceFiles(resourceBase);
            for (String resource : files) {
                InputStream in = InitProjectGenerator.class.getClassLoader().getResourceAsStream(resource);
                if (in == null) continue;

                String content = new String(in.readAllBytes(), StandardCharsets.UTF_8);
                for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                    content = content.replace("{{" + entry.getKey() + "}}", entry.getValue());
                }

                String relativePath = resource.replace(resourceBase + "/", "");
                Path outPath = Paths.get(outputDir, relativePath.replace(".template", ""));
                Files.createDirectories(outPath.getParent());
                Files.writeString(outPath, content, StandardOpenOption.CREATE);
                System.out.println("Created: " + outPath);
            }

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static List<String> listResourceFiles(String path) throws IOException, URISyntaxException {
        List<String> fileNames = new ArrayList<>();
        URL dirURL = InitProjectGenerator.class.getClassLoader().getResource(path);

        if (dirURL != null && dirURL.getProtocol().equals("file")) {
            Path folder = Paths.get(dirURL.toURI());
            Files.walk(folder)
                .filter(Files::isRegularFile)
                .forEach(p -> fileNames.add(path + "/" + folder.relativize(p).toString().replace("\\", "/")));
        } else if (dirURL != null && dirURL.getProtocol().equals("jar")) {
            String jarPath = dirURL.getPath().substring(5, dirURL.getPath().indexOf("!"));
            try (JarFile jar = new JarFile(URLDecoder.decode(jarPath, StandardCharsets.UTF_8))) {
                Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    String name = entries.nextElement().getName();
                    if (name.startsWith(path + "/") && !name.endsWith("/")) {
                        fileNames.add(name);
                    }
                }
            }
        }

        return fileNames;
    }
}
