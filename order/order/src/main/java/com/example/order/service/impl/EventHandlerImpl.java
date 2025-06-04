package com.example.order.service.impl;

import com.example.order.config.KafkaConfig;
import com.example.order.enums.InventoryStatus;
import com.example.order.events.InventoryEvent;
import com.example.order.events.PaymentEvent;
import com.example.order.service.Messenger;
import com.example.order.service.kafka.OrderKakfaProducer;
import org.springframework.stereotype.Service;

@Service
public class EventHandlerImpl implements Messenger {
    private final OrderKakfaProducer producer;
    private final KafkaConfig config;

    public EventHandlerImpl(OrderKakfaProducer producer, KafkaConfig config) {
        this.producer = producer;
        this.config = config;
    }

    @Override
    public void processInventoryEvent(InventoryEvent event) {
        if(event.getStatus() == InventoryStatus.Reserved)
        {
            //sending paymnet event to the payment service

        }
        else {
            //return logic for inventory failed
        }
    }

    @Override
    public void publishMsg(Object event) {
        String topic = null;
        if(event instanceof InventoryEvent)
            topic = config.getOrder();
        else if (event instanceof PaymentEvent)
            topic = config.getPayment();
        if(topic != null)
            producer.publishMsg(topic, event);
    }

    @Override
    public void processPaymentEvent(PaymentEvent pEvent) {

    }
}
