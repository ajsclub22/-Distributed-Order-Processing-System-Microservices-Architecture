package com.example.order.events;

import com.example.order.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEvent {
    private String clientMail;
    private OrderStatus status;
    private Long id;

}
