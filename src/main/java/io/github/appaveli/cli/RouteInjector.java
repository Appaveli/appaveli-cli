package io.github.appaveli.cli;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class RouteInjector {

    public static void inject(String entity, String webPackage) {
        String mainPath = "generated/Main.java";
        File mainFile = new File(mainPath);
        if (!mainFile.exists()) {
            System.out.println("⚠️ Main.java not found. Skipping route injection.");
            return;
        }

        try {
            List<String> lines = Files.readAllLines(mainFile.toPath());
            List<String> updated = new ArrayList<>();
            String route = "handler.addServlet(" + entity + "Servlet.class, \"/" + entity.toLowerCase() + "\");";
            boolean injected = false;
            for (String line : lines) {
                updated.add(line);
                if (!injected && line.contains("// Register servlets here")) {
                    updated.add(route);
                    injected = true;
                }
            }

            Files.write(mainFile.toPath(), updated);
            System.out.println("✅ Injected route for " + entity + "Servlet into Main.java");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
