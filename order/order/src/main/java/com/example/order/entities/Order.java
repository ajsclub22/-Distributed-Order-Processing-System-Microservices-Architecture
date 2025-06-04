package com.example.order.entities;


import com.example.order.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name ="product_id", nullable = false)
    private Long productId;

    @Column(name ="quantity", nullable = false)
    private Integer quantity;

    @Column(name ="order_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name ="created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name="order_price", nullable = false)
    private double price;

    @Column(name="client_id", nullable = false)
    private Long clientId;

    // This method will be called just before the entity is persisted
    //initially when the order is there is the
    //status should in the pending mode
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();

        this.status = OrderStatus.PENDING;
    }



}
