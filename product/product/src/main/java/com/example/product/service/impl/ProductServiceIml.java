package com.example.product.service.impl;

import com.example.product.constants.ProductConstant;
import com.example.product.dto.InventoryDTO;
import com.example.product.dto.ProductDTO;
import com.example.product.entities.Product;
import com.example.product.enums.ProductStatus;
import com.example.product.repo.ProductRepository;
import com.example.product.service.InventoryService;
import com.example.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ProductServiceIml implements ProductService {
    private final ProductRepository productRepository;
    private final InventoryService inventoryService;
    private final Logger logger =  LoggerFactory.getLogger(ProductService.class);

    public ProductServiceIml(ProductRepository productRepository, InventoryService inventoryService) {
        this.productRepository = productRepository;
        this.inventoryService = inventoryService;
    }

    @Override
    public ProductDTO addProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        logger.info("Product : " + product);
        logger.info("saved Successfully");
        return createDTO(product);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        return productList.stream().
                map(this::createDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (isProductNull(product)) {
            logger.warn(String.format("Product with id %d is not available", id));
            return null;
        } else {
            assert product != null;
            logger.info("Product : " + product);
            return createDTO(product);
        }
    }

    private ProductDTO createDTO(Product product)
    {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        InventoryDTO inventoryDTO = inventoryService.getInventoryByProductId(product.getId());
        if(isInventoryNull(inventoryDTO))
        {
            logger.warn(String.format("Product with id %d is not available in Inventory", product.getId()));
            dto.setStatus(ProductStatus.NOT_AVAILABLE);
        }
        else
        {
            Predicate<Integer> pred = i ->i>0;
            if(pred.test(inventoryDTO.getAvailableQuantity()))
            {
                logger.warn(String.format("Product with id %d is " +
                        "available in Inventory with quantity %d",
                        product.getId(), inventoryDTO.getAvailableQuantity()));
                dto.setStatus(ProductStatus.AVAILABLE);
            }
            else
            {
                logger.warn(String.format("Product with id %d is " +
                                "available in Inventory but Out " +
                                "of Stock", product.getId()));
                dto.setStatus(ProductStatus.OUT_OF_STOCK);
            }

        }
        return dto;
    }

    public boolean isProductNull(Product product){
        Predicate<Product> pred = Objects::isNull;
        return pred.test(product);
    }
    public boolean isInventoryNull(InventoryDTO inventoryDTO){
        Predicate<InventoryDTO> pred = Objects::isNull;
        return pred.test(inventoryDTO);
    }

}
