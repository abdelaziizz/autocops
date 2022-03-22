package com.mdp.autocops.model.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "service.port")
@Data
public class ServicePortProperties {
    private String OmniChannelsApi;
}
