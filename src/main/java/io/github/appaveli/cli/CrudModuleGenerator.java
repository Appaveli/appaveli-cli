package io.github.appaveli.cli;

public class CrudModuleGenerator {

    public static void generate(String entity, String basePackage, String fields) {
        String domainPackage = basePackage + ".domain";
        String daoPackage = basePackage + ".dao";
        String servicePackage = basePackage + ".service";
        String webPackage = basePackage + ".web";

        DomainGenerator.generate(entity, domainPackage, fields);
        DaoGenerator.generate(entity, daoPackage);
        ServiceGenerator.generate(entity, servicePackage);
        ServletGenerator.generate(entity, webPackage);
        JspGenerator.generate(entity, "list,form,edit,search");
        RouteInjector.inject(entity, webPackage);
    }
}
