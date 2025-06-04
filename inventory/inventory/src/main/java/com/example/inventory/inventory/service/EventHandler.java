package com.example.inventory.inventory.service;

import com.example.inventory.inventory.events.InventoryEvent;
import com.example.inventory.inventory.events.OrderEvent;

public interface EventHandler {
    void processOrderEvent(OrderEvent event);
    public InventoryEvent getIEvent(OrderEvent event);
}
