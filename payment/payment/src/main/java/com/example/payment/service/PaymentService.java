package com.example.payment.service;

import com.example.payment.events.PaymentEvent;

public interface PaymentService {
    void processPayment(PaymentEvent paymentEvent);
}
