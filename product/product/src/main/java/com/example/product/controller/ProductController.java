package com.example.product.controller;

import com.example.product.dto.ProductDTO;
import com.example.product.entities.Product;
import com.example.product.repo.ProductRepository;
import com.example.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping
    public ResponseEntity<ProductDTO> addProduct(@RequestBody Product product) {
        ProductDTO savedProduct = productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> productList = productService.getAllProducts();
        return ResponseEntity.ok(productList); // Return empty list with 200 is fine
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }


}
