package io.github.appaveli.cli;

public class CrudModuleGenerator {

    public static void generate(String entity, String basePackage, String fields) {
        String domainPackage = basePackage + ".domain";
        String daoPackage = basePackage + ".dao";
        String servicePackage = basePackage + ".service";
        String webPackage = basePackage + ".web";

        // Generate Domain class
        DomainGenerator.generate(entity, domainPackage, fields);

        // Generate DAO classes
        DaoGenerator.generate(entity, daoPackage);

        // Generate Service classes
        ServiceGenerator.generate(entity, servicePackage);

        // Generate Servlet
        ServletGenerator.generate(entity, webPackage);

        // Generate JSP views
        JspGenerator.generate(entity, webPackage);

        // Generate SQL script
        SqlGenerator.generate(entity, fields);

        // Inject servlet route into Main.java
        RouteInjector.inject(entity, webPackage);
    }
}
