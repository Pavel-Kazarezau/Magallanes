package io.kazarezau.magallanes.core.countries;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "integration.countries")
@Getter
@Setter
public class CountriesConfig {

    private String url;
}
