package com.example.order.events;


import com.example.order.enums.InventoryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryEvent {
    private Long orderId;
    private InventoryStatus status;
    private String reason;
    private Integer quantity;
    private Long productId;
    public InventoryEvent(InventoryEvent event)
    {
        this.orderId = event.getOrderId();
        this.status = event.getStatus();
        this.reason = event.getReason();
        this.productId = event.getProductId();
        this.quantity = event.getQuantity();
    }
}