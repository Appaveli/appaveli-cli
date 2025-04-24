/*
 * MIT License
 *
 * Copyright (c) 2024 AppaveliTech Solutions, LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 */
package tech.appavelitech.cli;

import java.util.Properties;

public class AppaveliCli {
    public static void main(String[] args) {
        if (args.length < 1) {
            printUsage();
            return;
        }

        String command = args[0];

        // Load defaults from config file
        Properties config = ConfigLoader.load(".appaveli-config.properties");

        switch (command) {
            case "generate-dao":
                handleGenerateDao(args);
                break;

            case "generate-domain":
                handleGenerateDomain(args);
                break;

            case "generate-jdbc":
                handleGenerateJdbc(args, config);
                break;

            case "generate-service":
                handleGenerateService(args);
                break;

            default:
                System.out.println("Unknown command: " + command);
                printUsage();
        }
    }

    private static void handleGenerateDao(String[] args) {
        String entity = null;
        String basePackage = null;

        for (int i = 1; i < args.length; i++) {
            switch (args[i]) {
                case "--entity":
                    if (i + 1 < args.length) entity = args[++i];
                    break;
                case "--package":
                    if (i + 1 < args.length) basePackage = args[++i];
                    break;
            }
        }

        if (entity == null || basePackage == null) {
            System.out.println("Missing required parameters for generate-dao.");
            printUsage();
            return;
        }

        DaoGenerator.generate(entity, basePackage);
    }
    private static void handleGenerateService(String[] args) {
        String service = null;
        String basePackage = null;

        for (int i = 1; i < args.length; i++) {
            switch (args[i]) {
                case "--service":
                    if (i + 1 < args.length) service = args[++i];
                    break;
                case "--package":
                    if (i + 1 < args.length) basePackage = args[++i];
                    break;
            }
        }

        if (service == null || basePackage == null) {
            System.out.println("Missing required parameters for generate-service.");
            printUsage();
            return;
        }

        ServiceGenerator.generate(service, basePackage);
    }
    private static void handleGenerateDomain(String[] args) {
        String entity = null;
        String basePackage = null;
        String fields = null;

        for (int i = 1; i < args.length; i++) {
            switch (args[i]) {
                case "--entity":
                    if (i + 1 < args.length) entity = args[++i];
                    break;
                case "--package":
                    if (i + 1 < args.length) basePackage = args[++i];
                    break;
                case "--fields":
                    if (i + 1 < args.length) fields = args[++i];
                    break;
            }
        }

        if (entity == null || basePackage == null || fields == null) {
            System.out.println("Missing required parameters for generate-domain.");
            printUsage();
            return;
        }

        DomainGenerator.generate(entity, basePackage, fields);
    }

    private static void handleGenerateJdbc(String[] args, Properties config) {
        String basePackage = null;
        String dbName = config.getProperty("db.name", "your_database");
        String dbUser = config.getProperty("db.user", "your_user");
        String dbPassword = config.getProperty("db.password", "your_password");

        for (int i = 1; i < args.length; i++) {
            switch (args[i]) {
                case "--package":
                    if (i + 1 < args.length) basePackage = args[++i];
                    break;
                case "--db-name":
                    if (i + 1 < args.length) dbName = args[++i];
                    break;
                case "--db-user":
                    if (i + 1 < args.length) dbUser = args[++i];
                    break;
                case "--db-password":
                    if (i + 1 < args.length) dbPassword = args[++i];
                    break;
                default:
                    System.out.println("Unknown argument for generate-jdbc: " + args[i]);
                    printUsage();
                    return;
            }
        }

        if (basePackage == null) {
            System.out.println("Missing required --package for generate-jdbc.");
            printUsage();
            return;
        }

        System.out.println("[DEBUG] Calling JdbcUtilGenerator...");
        JdbcUtilGenerator.generate(basePackage, dbName, dbUser, dbPassword);
    }

    private static void printUsage() {
        System.out.println("\nUsage:");
        System.out.println("  generate-dao --entity EntityName --package your.package.name");
        System.out.println("  generate-service --service ServiceName --package your.package.name");
        System.out.println("  generate-domain --entity EntityName --package your.package.name --fields \"name:String,email:String\"");
        System.out.println("  generate-jdbc --package your.package.name [--db-name db] [--db-user user] [--db-password pass]");
        System.out.println("\nOptional: define defaults in .appaveli-config.properties\n");
    }
}