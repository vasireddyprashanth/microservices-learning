package com.prash.ecommerce.order_service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "my")
@Getter
@Setter
public class PropertiesFile {
    private String variable;
}
