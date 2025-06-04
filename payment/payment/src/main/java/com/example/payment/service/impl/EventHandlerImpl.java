package com.example.payment.service.impl;

import com.example.payment.config.KafkaConfig;
import com.example.payment.enums.PaymentStatus;
import com.example.payment.events.PaymentEvent;
import com.example.payment.service.EventHandler;
import com.example.payment.service.kafka.PaymentKafkaProducer;
import org.springframework.stereotype.Service;

@Service
public class EventHandlerImpl implements EventHandler {
    private final PaymentKafkaProducer producer;
    private final KafkaConfig config;

    public EventHandlerImpl(PaymentKafkaProducer producer, KafkaConfig config) {
        this.producer = producer;
        this.config = config;
    }

    @Override
    public void processPayment(PaymentEvent paymentEvent) {
        //get payment details,
        // redirect the client to the pauyment gateway page
        //check for success or failure and send back
        //the kafka events to the order service
        //for testing set  the sucess
        paymentEvent.setStatus(PaymentStatus.SUCCESS);
        handlePaymentEvent(paymentEvent);
    }

    @Override
    public void handlePaymentEvent(PaymentEvent paymentEvent) {
        PaymentEvent pEvent = new PaymentEvent(paymentEvent);
        publishMsg(pEvent);
    }

    private void publishMsg(Object event)
    {
        String topic = null;
        if(event instanceof PaymentEvent)
            topic = config.getOrder();

        if(topic != null)
            producer.publishMsg(topic, event);

    }
}
