package com.example.order.events;

import com.example.order.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEvent {
    private Long orderId;
    private double price;
    private Long clientId;
    private PaymentStatus status;

    public PaymentEvent(PaymentEvent event)
    {
        this.price = event.getPrice();
        this.clientId = event.getClientId();
        this.orderId = event.getOrderId();
        this.status = event.getStatus();
    }
}
