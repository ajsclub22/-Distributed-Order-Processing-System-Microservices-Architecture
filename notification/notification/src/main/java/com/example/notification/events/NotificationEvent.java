package com.example.notification.events;

import com.example.notification.enums.OrderStatus;
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
