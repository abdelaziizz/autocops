package com.mdp.autocops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
@EntityScan(basePackages = {"com.mdp.autocops.model.entity"})
public class AutocopsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutocopsApplication.class, args);
    }

}
