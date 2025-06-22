package com.example.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "kafka.topics")
@Data
public class KafkaConfig {
    private Inventory inventory;
    private Payment payment;
    private Notification notify;

    @Data
    public static class Inventory {
        private String request;
        private String response;
    }

    @Data
    public static class Payment {
        private String request;
        private String response;
    }

    @Data
    public static class Notification{
        private String request;
    }
}
