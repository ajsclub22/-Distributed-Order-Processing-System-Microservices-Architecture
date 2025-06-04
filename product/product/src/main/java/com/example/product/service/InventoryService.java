package com.example.product.service;

import com.example.product.dto.InventoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@FeignClient(url = "http://localhost:8083", value = "INVENTORY-SERVICE")
public interface InventoryService {
    @GetMapping("/inventory/product/{id}")
    InventoryDTO getInventoryByProductId(@PathVariable("id") Long id);
}