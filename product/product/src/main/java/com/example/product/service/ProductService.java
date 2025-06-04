package com.example.product.service;


import com.example.product.dto.ProductDTO;
import com.example.product.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {


    ProductDTO addProduct(Product product);
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(Long id);  // Matches controller
}
