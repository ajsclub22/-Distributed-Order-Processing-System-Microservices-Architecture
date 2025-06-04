package com.example.payment.service.kafka;

import com.example.payment.events.InventoryEvent;
import com.example.payment.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentKafkaConsumer {
    private final ObjectMapper mapper = new ObjectMapper();
    private final PaymentService service;

    public PaymentKafkaConsumer(PaymentService service) {
        this.service = service;
    }

    @KafkaListener(topics = "inventory-reserved", groupId = "payment-group")
    public void getInventoryEvent(String event)
    {
        try{
            InventoryEvent inventory = mapper.readValue(event, InventoryEvent.class);
            service.processPayment(inventory);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
