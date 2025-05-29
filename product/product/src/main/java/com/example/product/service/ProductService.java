package com.example.product.service;


import com.example.product.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {


    Product addProduct(Product product);
    List<Product> getAllProducts();
    Product getProductById(Long id);  // Matches controller
}
