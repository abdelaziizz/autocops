package com.mdp.autocops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties
@EntityScan(basePackages = {"com.mdp.autocops.model.*"})
public class AutocopsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutocopsApplication.class, args);
    }

}
