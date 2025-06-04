package com.example.inventory.inventory.controller;

import com.example.inventory.inventory.dto.InventoryDTO;
import com.example.inventory.inventory.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/{id}")
    public InventoryDTO getInventoryById(@PathVariable("id") Long id)
    {
        return inventoryService.getInventoryById(id);
    }

    @GetMapping("/product/{id}")
    public InventoryDTO getInventoryByProductId(@PathVariable("id") Long id){
        System.out.println(inventoryService.getInventoryByProductId(id));
        return inventoryService.getInventoryByProductId(id);
    }
}
