package com.example.inventory.inventory.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "kafka.topics")
@Data
public class KafkaConfig {
    private Inventory inventory;

    @Data
    public static class Inventory {
        private String request;
        private String response;
    }
}
