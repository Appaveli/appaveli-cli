package io.github.appaveli.cli;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class RouteInjector {

    public static void inject(String entity, String webPackage) {
        String mainPath = "generated/Main.java";
        File mainFile = new File(mainPath);

        // Create Main.java if it doesn't exist
        if (!mainFile.exists()) {
            try {
                String mainTemplate = """
package generated;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletContextHandler handler = new ServletContextHandler(server, "/");

        // Register servlets here

        server.start();
        server.join();
    }
}
""".strip();

                Files.createDirectories(mainFile.getParentFile().toPath());
                Files.writeString(mainFile.toPath(), mainTemplate);
                System.out.println("✅ Main.java was created at: " + mainPath);
            } catch (IOException e) {
                System.out.println("❌ Failed to create Main.java");
                e.printStackTrace();
                return;
            }
        }

        try {
            List<String> lines = Files.readAllLines(mainFile.toPath());
            List<String> updated = new ArrayList<>();
            String route = "        handler.addServlet(" + entity + "Servlet.class, \"/" + entity.toLowerCase() + "\");";

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