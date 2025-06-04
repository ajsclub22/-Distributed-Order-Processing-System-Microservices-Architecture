package com.example.inventory.inventory.service;

import com.example.inventory.inventory.dto.InventoryDTO;
import com.example.inventory.inventory.entities.Inventory;

public interface InventoryService {
    InventoryDTO getInventoryById(Long id);

    InventoryDTO getInventoryByProductId(Long id);
}
