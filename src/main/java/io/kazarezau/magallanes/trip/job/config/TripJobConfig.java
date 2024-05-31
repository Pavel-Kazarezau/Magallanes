package io.kazarezau.magallanes.trip.job.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "scheduled.trip")
@Getter
@Setter
public class TripJobConfig {

    private String activation;

    private String completing;
}
