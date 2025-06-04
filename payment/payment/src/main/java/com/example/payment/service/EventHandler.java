package com.example.payment.service;

import com.example.payment.events.PaymentEvent;

public interface EventHandler {

    void processPayment(PaymentEvent paymentEvent);

    void handlePaymentEvent(PaymentEvent paymentEvent);
}
