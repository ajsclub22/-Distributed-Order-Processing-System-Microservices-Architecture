package com.example.payment.service.impl;

import com.example.payment.config.KafkaConfig;
import com.example.payment.entities.Payment;
import com.example.payment.enums.PaymentStatus;
import com.example.payment.events.PaymentEvent;
import com.example.payment.repo.PaymentRepository;
import com.example.payment.service.PaymentService;
import com.example.payment.service.kafka.PaymentKafkaProducer;

public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository repo;

    public PaymentServiceImpl(PaymentRepository repo) {
        this.repo = repo;
    }

    @Override
    public void processPayment(PaymentEvent paymentEvent) {
        Payment payment = new Payment();
        System.out.println("Payment Done");
        payment.setPrice(paymentEvent.getPrice());
        payment.setStatus(PaymentStatus.SUCCESS);
        repo.save(payment);
        //producer.publishMsg(config.getInventory(),);

    }
}
