# appaveli-cli

**appaveli-cli** is a lightweight Java-based code generation tool that helps you scaffold DAO interfaces, DAO implementations, and domain model classes using simple CLI commands.

Built with developers in mind, it accelerates backend development by generating boilerplate code based on customizable templates.

---

## 🚀 Features

- ✅ Generate DAO interfaces and implementations (JDBC-style)
- ✅ Generate domain classes with fields, getters/setters, and `toString()`
- ✅ Generate DatabaseConnection and JDBC utility class
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
````

```bash
git clone https://github.com/appavelitech/appaveli-cli.git
cd appaveli-cli
mvn clean install
```
## 📦 CLI Usage

### 🔹 Generate JDBC Utility

```bash
java -jar target/appaveli-cli-jar-with-dependencies.jar generate-jdbc --package com.example.dao

```
Creates:
* 	DatabaseConnection.java
---

### 🔹 Generate SQL

```bash
java -jar target/appaveli-cli-jar-with-dependencies.jar generate-sql --entity User --fields "id:int,username:String,email:String"

```
Creates:
* 	User.sql
```bash
CREATE TABLE user (
    id INT PRIMARY KEY,
    username VARCHAR(255),
    email VARCHAR(255)
);

```

---
### 🔹 Generate DAO

```bash
java -jar target/appaveli-cli-jar-with-dependencies.jar generate-dao \
  --entity User \
  --package com.example.dao
```
Creates:
* 	UserDao.java
* 	UserDaoImpl.java
---

### 🔹 Generate Domain Class

```bash
java -jar target/appaveli-cli-jar-with-dependencies.jar generate-domain \
  --entity User \
  --package com.example.domain \
  --fields "id:int,username:String,email:String,active:boolean"
```
Creates:
* User.java with fields, getters/setters, and toString()
---

## 🌐 Optional: Global CLI Access
 Move or symlink the JAR:
   ```bash
   cp target/appaveli-cli-jar-with-dependencies.jar ~/.local/bin/appaveli-cli.jar
   ```
Create a wrapper script:
```bash
   cp target/appaveli-cli-jar-with-dependencies.jar ~/.local/bin/appaveli-cli.jar
   ```
Ensure ~/.local/bin is in your PATH:
```bash
echo 'export PATH="$HOME/.local/bin:$PATH"' >> ~/.bashrc
source ~/.bashrc   
```
Now you can run:
```bash
appaveli-cli generate-dao --entity Foo --package tech.example.dao
```
---

## 📁 Templates

All templates are located in the /templates directory and use {{PLACEHOLDER}} syntax. You can modify these or extend the CLI to support custom template paths in future versions.

---

## 📜 License

This project is licensed under the [MIT License](LICENSE).

---
## 🤝 Contributing

We welcome contributions! Feel free to open issues or submit pull requests to improve features, templates, or documentation.
