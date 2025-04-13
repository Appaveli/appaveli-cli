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

public class AppaveliCli {
    public static void main(String[] args) {
        if (args.length < 3) {
            printUsage();
            return;
        }

        String command = args[0];
        String entity = null;
        String basePackage = null;
        String fields = null;

        for (int i = 1; i < args.length; i++) {
            switch (args[i]) {
                case "--entity":
                    entity = args[i + 1];
                    i++;
                    break;
                case "--package":
                    basePackage = args[i + 1];
                    i++;
                    break;
                case "--fields":
                    fields = args[i + 1];
                    i++;
                    break;
            }
        }

        if (entity == null || basePackage == null) {
            System.out.println("Missing required parameters.");
            printUsage();
            return;
        }

        switch (command) {
            case "generate-dao":
                DaoGenerator.generate(entity, basePackage);
                break;

            case "generate-domain":
                if (fields == null) {
                    System.out.println("--fields is required for generate-domain");
                    printUsage();
                    return;
                }
                DomainGenerator.generate(entity, basePackage, fields);
                break;

            default:
                System.out.println("Unknown command: " + command);
                printUsage();
        }
    }

    private static void printUsage() {
        System.out.println("\nUsage:");
        System.out.println("  generate-dao --entity EntityName --package your.package.name");
        System.out.println("  generate-domain --entity EntityName --package your.package.name --fields \"name:String,email:String\"\n");
    }
}