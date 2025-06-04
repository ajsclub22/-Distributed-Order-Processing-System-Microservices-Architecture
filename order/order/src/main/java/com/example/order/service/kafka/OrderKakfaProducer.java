package com.example.order.kafka;

import com.example.order.events.OrderEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderKakfaProducer {

    private final KafkaTemplate<String, String> kafkaProducer;
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    @Value("${producer.topic.order.name}")
    private String topic;

    public OrderKakfaProducer(KafkaTemplate<String, String> kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    public void publishMsg(OrderEvent orderEvent)
    {
        try {
            String event = mapper.writeValueAsString(orderEvent);
            kafkaProducer.send(topic, event);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
