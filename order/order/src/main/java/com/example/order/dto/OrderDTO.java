package com.example.order.dto;

import com.example.order.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private String message;
    private Long OrderId;
    private OrderStatus status;
    private Integer quantity;
    private Double price;
}
