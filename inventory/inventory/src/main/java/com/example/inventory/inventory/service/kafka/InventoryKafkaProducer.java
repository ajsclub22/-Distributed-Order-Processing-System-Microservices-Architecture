package com.example.inventory.inventory.service.kafka;

import com.example.inventory.inventory.events.InventoryEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class InventoryKafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    ObjectMapper mapper = new ObjectMapper();

    public InventoryKafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishMsg(String topic, InventoryEvent msgEvent){
        try
        {
            String msg = mapper.writeValueAsString(msgEvent);
            kafkaTemplate.send(topic, msg);
            System.out.println("publish mgs:- "+msg);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
