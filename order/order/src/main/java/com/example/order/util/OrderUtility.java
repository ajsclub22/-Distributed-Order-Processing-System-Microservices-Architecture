package com.example.order.util;

import com.example.order.dto.OrderDTO;
import com.example.order.entities.Order;
import com.example.order.enums.OrderStatusMessages;
import com.example.order.events.InventoryEvent;
import com.example.order.events.NotificationEvent;
import com.example.order.events.PaymentEvent;

public interface OrderUtility {
     static InventoryEvent getInventoryEvent(Order order)
    {
        InventoryEvent event = new InventoryEvent();
        event.setOrderId(order.getId());
        event.setQuantity(order.getQuantity());
        event.setProductId(order.getProductId());
        return event;
    }
    static PaymentEvent getPaymentEvent(Order order) {
        PaymentEvent event = new PaymentEvent();
        event.setClientId(order.getClientId());
        event.setPrice(order.getPrice());
        event.setOrderId(order.getId());
        return event;

    }
    static OrderDTO getOrderDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setMessage(OrderStatusMessages.getMessage(order.getStatus()));
        dto.setOrderId(order.getId());
        dto.setStatus(order.getStatus());
        dto.setQuantity(order.getQuantity());
        dto.setPrice(order.getPrice());
        return dto;
    }
    static NotificationEvent getNotificationEvent(Order order)
    {
        NotificationEvent event = new NotificationEvent();
        event.setId(order.getId());
        event.setStatus(order.getStatus());
        event.setClientMail(order.getEmail());
        return event;
    }
}
