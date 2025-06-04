package com.example.order.service;

import com.example.order.events.InventoryEvent;
import com.example.order.events.OrderEvent;
import com.example.order.events.PaymentEvent;

public interface EventHandler {
    void processInventoryEvent(InventoryEvent ievent);

    void publishMsg(Object event);

    void processPaymentEvent(PaymentEvent pEvent);
}
