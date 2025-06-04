package com.example.inventory.inventory.service.kafka;

import com.example.inventory.inventory.events.OrderEvent;
import com.example.inventory.inventory.service.Messenger;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class InventorykafkaConsumer {
    private final Messenger messenger;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public InventorykafkaConsumer(Messenger messenger) {
        this.messenger = messenger;
    }

    @KafkaListener(topics = "order-created", groupId = "inventory-group")
    public void processOrderEvent(String eventJson)
    {
        try
        {
            System.out.println(eventJson);
            OrderEvent event = objectMapper.readValue(eventJson, OrderEvent.class);
            messenger.processOrderEvent(event);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("in exception");
        }
    }
}
