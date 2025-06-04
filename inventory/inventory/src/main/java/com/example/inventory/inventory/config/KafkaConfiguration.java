package com.example.inventory.inventory.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "kafka.topics")
@Data
public class KafkaConfiguration {
    private String order;
    private String inventory;
}
