package com.example.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "kafka.topics")
@Component
public class KafkaConfig {
    private String order;
    private String payment;
    private String inventory;
    private String notify;
}
