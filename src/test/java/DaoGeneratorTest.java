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

import org.junit.jupiter.api.Test;
import tech.appavelitech.cli.DaoGenerator;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class DaoGeneratorTest {

    @Test
    void test_dao_generation_creates_files() throws Exception {
        Path templateDir = Files.createTempDirectory("templates");
        Path daoInterfaceTemplate = templateDir.resolve("DaoInterface.template");
        Path daoImplTemplate = templateDir.resolve("DaoImpl.template");

        Files.writeString(daoInterfaceTemplate, "package {{PACKAGE}};\npublic interface {{ENTITY}}Dao {}");
        Files.writeString(daoImplTemplate, "package {{PACKAGE}};\npublic class {{ENTITY}}DaoImpl implements {{ENTITY}}Dao {}");

        System.setProperty("user.dir", templateDir.getParent().toString()); // simulate working directory

        DaoGenerator.generate("User", "com.example");

        File interfaceFile = new File("generated/com/example/UserDao.java");
        File implFile = new File("generated/com/example/UserDaoImpl.java");

        assertTrue(interfaceFile.exists(), "Interface file should be created");
        assertTrue(implFile.exists(), "Impl file should be created");
    }
}
