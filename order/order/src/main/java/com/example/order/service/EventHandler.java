package com.example.order.service;

import com.example.order.entities.Order;
import com.example.order.events.InventoryEvent;
import com.example.order.events.OrderEvent;
import com.example.order.events.PaymentEvent;

public interface EventHandler {
    void processInventoryEvent(InventoryEvent ievent);


    void handlePaymentEvent(Order order);

    void processPaymentEvent(PaymentEvent pEvent);

    void handleNotifiactionEvent(Order order);

    void handleInventoryEvent(Order order);
}
