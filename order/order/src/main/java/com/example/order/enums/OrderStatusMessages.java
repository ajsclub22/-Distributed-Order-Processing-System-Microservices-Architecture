package com.example.order.enums;

import java.util.EnumMap;
import java.util.Map;

public class OrderStatusMessages {

    private static final Map<OrderStatus, String> messages = new EnumMap<>(OrderStatus.class);

    static {
        messages.put(OrderStatus.PENDING, "Order received and is pending processing.");
        messages.put(OrderStatus.PROCESSING, "Order is currently being processed.");
        messages.put(OrderStatus.SHIPPED, "Order has been shipped to the delivery address.");
        messages.put(OrderStatus.DELIVERED, "Order has been delivered successfully.");
        messages.put(OrderStatus.CANCELLED, "Order has been cancelled.");
    }

    public static String getMessage(OrderStatus status) {
        return messages.getOrDefault(status, "Unknown order status.");
    }
}
