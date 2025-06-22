package com.example.payment.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "kafka.topics")
@Data
public class KafkaConfig {
    private Payment payment;


    @Data
    public static class Payment {
        private String request;
        private String response;
    }
}