package com.example.order.service.kafka;

import com.example.order.events.OrderEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderKakfaProducer {

    private final KafkaTemplate<String, String> kafkaProducer;
    private final ObjectMapper mapper = new ObjectMapper();

    public OrderKakfaProducer(KafkaTemplate<String, String> kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    public void publishMsg(String topic, Object orderEvent)
    {
        try {
            String event = mapper.writeValueAsString(orderEvent);
            kafkaProducer.send(topic, event);
            System.out.println("------------------------------------------------------------------------------------------kafka produce-----------------------");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
