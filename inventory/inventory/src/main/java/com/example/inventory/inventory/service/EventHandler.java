package com.example.inventory.inventory.service;

import com.example.inventory.inventory.events.InventoryEvent;

public interface EventHandler {
    void processOrderEvent(InventoryEvent event);

    void handleInventoryEvent(InventoryEvent event);
}
