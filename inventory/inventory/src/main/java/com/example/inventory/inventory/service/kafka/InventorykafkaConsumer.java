package com.example.inventory.inventory.service.kafka;

import com.example.inventory.inventory.events.InventoryEvent;
import com.example.inventory.inventory.service.EventHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class InventorykafkaConsumer {
    private final EventHandler handler;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public InventorykafkaConsumer(EventHandler handler) {
        this.handler = handler;
    }

    @KafkaListener(topics = "${kafka.topics.inventory.request}", groupId = "${kafka.groups.inventory}")
    public void processOrderEvent(String eventJson)
    {
        try
        {
            System.out.println(eventJson);
            InventoryEvent event = objectMapper.readValue(eventJson, InventoryEvent.class);
            handler.processOrderEvent(event);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
