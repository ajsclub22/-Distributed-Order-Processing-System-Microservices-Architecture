package com.example.order.service;


import com.example.order.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:8082", value = "PRODUCT-SERVICE")
@Service
public interface ProductValidateService {

    @GetMapping("/product/{id}")
    public ProductDTO getProductById(@PathVariable("id") Long id);
}
