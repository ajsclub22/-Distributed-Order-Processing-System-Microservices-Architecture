package com.example.order.service.kafka;

import com.example.order.config.KafkaConfig;
import com.example.order.events.InventoryEvent;
import com.example.order.events.PaymentEvent;
import com.example.order.service.EventHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderKafkaConsumer {
    private final EventHandler handler;

    private final ObjectMapper mapper;

    public OrderKafkaConsumer(EventHandler handler, KafkaConfig config) {
        this.handler = handler;
        this.mapper = new ObjectMapper();
    }

    @KafkaListener(topics = "${kafka.topics.payment.response}", groupId = "${kafka.consumer.group.id}")
    public void processPayment(String event) {
        try {
            PaymentEvent pEvent = mapper.readValue(event, PaymentEvent.class);
            handler.processPaymentEvent(pEvent);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = "${kafka.topics.inventory.response}", groupId = "${kafka.consumer.group.id}")
    public void processInventoryEvent(String event){
        // get the inventory event and process them for further processing
        try {
            InventoryEvent ievent = mapper.readValue(event, InventoryEvent.class);
            handler.processInventoryEvent(ievent);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
