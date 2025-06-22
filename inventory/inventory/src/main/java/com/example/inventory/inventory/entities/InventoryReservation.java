package com.example.inventory.inventory.entities;

import com.example.inventory.inventory.enums.InventoryStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inventory_reservation")
public class InventoryReservation {
    @Id
    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "inventory_status")
    @Enumerated(EnumType.STRING)
    private InventoryStatus status;


}
