# appaveli-cli

[![Maven Central](https://img.shields.io/maven-central/v/io.github.appaveli/appaveli-cli.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/io.github.appaveli/appaveli-cli)

**appaveli-cli** is a lightweight Java-based code generation tool that helps you scaffold DAO interfaces, DAO implementations, domain model classes, servlets, authentication, and entire project skeletons using simple CLI commands.

Built with developers in mind, it accelerates backend development by generating boilerplate code based on customizable templates — without requiring frameworks.

---

## 🚀 Features

- ✅ Generate DAO interfaces and implementations (JDBC-style)
- ✅ Generate domain classes with fields, getters/setters, and `toString()`
- ✅ Generate DatabaseConnection and JDBC utility class
- ✅ Generate servlets, JSPs, and authentication routes
- ✅ Scaffold login/logout flows and session/password utils
- ✅ Inject servlet routes into Main.java automatically
- ✅ Template-based code generation
- ✅ Framework-agnostic (use it in any Java project)
- ✅ Easy to use and customize

---

## ⚙️ Requirements

- **Java 17 or higher**

> ⚠️ If you see an error like `UnsupportedClassVersionError`, your system is likely using an older Java runtime than the one used to compile the project. Either upgrade your JDK or modify the build settings in `pom.xml` to target a lower version.

Check your Java version:

```bash
java -version
```

---
## ⚡️ Quick Start (via Shell Script)

The easiest way to use **Appaveli CLI** without cloning the repo is by running it directly via shell script. This script will download the JAR from Maven Central and execute the CLI.

```bash
curl -sSL https://raw.githubusercontent.com/appavelitech/appaveli-cli/main/appaveli-cli.sh -o appaveli-cli.sh
chmod +x appaveli-cli.sh
./appaveli-cli.sh generate-dao --entity User --package com.example.dao
```
## 📦 CLI Usage

### 🔹 Setup

```bash
git clone https://github.com/appavelitech/appaveli-cli.git
cd appaveli-cli
mvn clean install
```

---

## 🧰 Available CLI Commands

### Core Code Generation

```bash
generate-dao --entity EntityName --package com.package.dao
generate-domain --entity EntityName --package com.package.domain --fields "name:String,email:String"
generate-service --entity EntityName --package com.package.service
generate-api --entity EntityName --package com.package.api --dao-package com.package.dao --domain-package com.package.domain --util-package com.package.util
```

### JSP and Web Layer

```bash
generate-jsp --entity EntityName --package com.package.web
```

### Authentication

```bash
generate-auth --entity User --package com.package.auth --dao-package com.package.dao --domain-package com.package.domain
generate-auth-utils --entity User --package com.package.util --domain-package com.package.domain
generate-auth-servlet --entity User --package com.package.auth --dao-package com.package.dao --domain-package com.package.domain --util-package com.package.util
```

### Project Bootstrapping

```bash
java -jar target/appaveli-cli.jar init-project --name MyApp --package com.package
```

---

### 🔹 Generate JDBC Utility

```bash
java -jar target/appaveli-cli.jar generate-jdbc --package com.example.dao
```

Creates:
* DatabaseConnection.java

---

### 🔹 Generate SQL

```bash
java -jar target/appaveli-cli.jar generate-sql --entity User --fields "id:int,username:String,email:String"
```

Creates:
* User.sql

```sql
CREATE TABLE user (
    id INT PRIMARY KEY,
    username VARCHAR(255),
    email VARCHAR(255)
);
```

---

### 🔹 Generate DAO

```bash
java -jar target/appaveli-cli.jar generate-dao --entity User --package com.example.dao
```

Creates:
* UserDao.java
* UserDaoImpl.java

---

### 🔹 Generate Domain Class

```bash
java -jar target/appaveli-cli.jar generate-domain --entity User --package com.example.domain --fields "id:int,username:String,email:String,active:boolean"
```

Creates:
* User.java with fields, getters/setters, and toString()

---

## 🌐 Optional: Global CLI Access

Move or symlink the JAR:

```bash
cp target/appaveli-cli.jar ~/.local/bin/appaveli-cli.jar
```

Create a wrapper script or alias:

```bash
echo 'alias appaveli-cli="java -jar ~/.local/bin/appaveli-cli.jar"' >> ~/.bashrc
source ~/.bashrc
```

Now you can run:

```bash
appaveli-cli generate-dao --entity Foo --package tech.example.dao
```

---

## 📁 Templates

All templates are located in the `/templates` directory and use `{{PLACEHOLDER}}` syntax. You can modify these or extend the CLI to support custom template paths in future versions.

---

## 📜 License

This project is licensed under the [MIT License](LICENSE).

---

## 🤝 Contributing

We welcome contributions! Feel free to open issues or submit pull requests to improve features, templates, or documentation.
