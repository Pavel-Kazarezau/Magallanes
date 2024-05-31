package io.kazarezau.magallanes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.modulith.Modulithic;

@SpringBootApplication
@EnableFeignClients
@Modulithic(
        sharedModules = {
                "io.kazarezau.magallanes.core",
        },
        useFullyQualifiedModuleNames = true
)
public class MagallanesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MagallanesApplication.class, args);
    }

}
