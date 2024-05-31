package io.kazarezau.magallanes;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

class ApplicationModulesTest {

    @Test
    void createApplicationModuleModel() {
        ApplicationModules modules = ApplicationModules.of(MagallanesApplication.class);
        modules.forEach(System.out::println);
    }

    @Test
    void verifiesModularStructure() {
        ApplicationModules modules = ApplicationModules.of(MagallanesApplication.class);
        modules.verify();
    }

    @Test
    void createModuleDocumentation() {
        ApplicationModules modules = ApplicationModules.of(MagallanesApplication.class);
        new Documenter(modules)
                .writeDocumentation()
                .writeIndividualModulesAsPlantUml();
    }
}
