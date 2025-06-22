package com.example.payment.service.kafka;

import com.example.payment.events.PaymentEvent;
import com.example.payment.service.EventHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentKafkaConsumer {
    private final ObjectMapper mapper = new ObjectMapper();
    private final EventHandler handler;

    public PaymentKafkaConsumer(EventHandler handler) {
        this.handler = handler;
    }

    @KafkaListener(topics = "${kafka.topics.payment.request}", groupId = "${kafka.consumer.group.id}")
    public void getPaymentEvent(String event)
    {
        //receive the payment event  from the order service
        try{
            PaymentEvent paymentEvent = mapper.readValue(event, PaymentEvent.class);
            handler.processPayment(paymentEvent);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
