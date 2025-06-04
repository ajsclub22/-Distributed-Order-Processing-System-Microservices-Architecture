package com.example.inventory.inventory.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "product_id" ,unique = true, nullable = false)
    private Long productId;

    @Column(name = "total_quantity", nullable = false)
    private Integer totalQuantity;

    @Column(name = "reserved_quantity", nullable = false)
    private Integer reservedQuantity;
    public int getAvailableQuantity()
    {
        return totalQuantity - reservedQuantity;
    }
}
