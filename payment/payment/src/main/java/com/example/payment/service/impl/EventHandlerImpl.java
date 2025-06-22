package com.example.payment.service.impl;

import com.example.payment.config.KafkaConfig;
import com.example.payment.entities.Payment;
import com.example.payment.events.PaymentEvent;
import com.example.payment.repo.PaymentRepository;
import com.example.payment.service.EventHandler;
import com.example.payment.service.StripeService;
import com.example.payment.service.kafka.PaymentKafkaProducer;
import org.springframework.stereotype.Service;

@Service
public class EventHandlerImpl implements EventHandler {
    private final PaymentKafkaProducer producer;
    private final KafkaConfig config;
    private final StripeService stripeService;
    private final PaymentRepository repo;

    public EventHandlerImpl(PaymentKafkaProducer producer, KafkaConfig config, StripeService stripeService, PaymentRepository repo) {
        this.producer = producer;
        this.config = config;
        this.stripeService = stripeService;
        this.repo = repo;
    }

    @Override
    public void processPayment(PaymentEvent paymentEvent) {
        //get payment details,
        // redirect the client to the pauyment gateway page
        //check for success or failure and send back
        //the kafka events to the order service
        //for testing set  the success
        try {
            stripeService.createSession(paymentEvent);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void handlePaymentEvent(PaymentEvent paymentEvent) {
        PaymentEvent pEvent = new PaymentEvent(paymentEvent);
        Payment payment = new Payment();
        payment.setOrderId(paymentEvent.getOrderId());
        payment.setPrice(paymentEvent.getPrice());
        payment.setStatus(paymentEvent.getStatus());
        repo.save(payment);
        publishMsg(pEvent);
    }

    private void publishMsg(Object event)
    {
        String topic = null;
        if(event instanceof PaymentEvent)
            topic = config.getPayment().getResponse();

        if(topic != null)
            producer.publishMsg(topic, event);

    }

}
