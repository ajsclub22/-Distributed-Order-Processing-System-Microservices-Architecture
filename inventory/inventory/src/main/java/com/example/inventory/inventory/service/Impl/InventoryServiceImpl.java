package com.example.inventory.inventory.service.Impl;

import com.example.inventory.inventory.dto.InventoryDTO;
import com.example.inventory.inventory.entities.Inventory;
import com.example.inventory.inventory.repo.InventoryRepository;
import com.example.inventory.inventory.service.InventoryService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepo;

    public InventoryServiceImpl(InventoryRepository inventoryRepo) {
        this.inventoryRepo = inventoryRepo;
    }

    @Override
    public InventoryDTO getInventoryById(Long id) {
        Optional<Inventory> optional = inventoryRepo.findById(id);
        if(optional.isPresent())
        {
            Inventory inventory = optional.get();
            return new InventoryDTO(inventory.getProductId(), inventory.getAvailableQuantity());
        }
        else
        {
            return null;
        }
    }

    @Override
    public InventoryDTO getInventoryByProductId(Long id) {
        Optional<Inventory> optional = inventoryRepo.findByProductId(id);
        if(optional.isPresent())
        {
            Inventory inventory = optional.get();
            return new InventoryDTO(inventory.getProductId(), inventory.getAvailableQuantity());
        }
        else
        {
            return null;
        }
    }







}
