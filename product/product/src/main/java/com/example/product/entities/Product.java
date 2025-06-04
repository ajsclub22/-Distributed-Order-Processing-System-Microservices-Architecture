package com.example.product.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name ="name", nullable = false)
    private String name;

    @Column(name ="description", nullable = false)
    private String description;
    @Column(name ="sku_code", nullable = false)
    private String skuCode;
    @Column(name ="price", nullable = false)
    private double price;

}
