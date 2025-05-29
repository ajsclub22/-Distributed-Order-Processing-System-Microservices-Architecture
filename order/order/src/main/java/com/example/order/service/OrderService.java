package com.example.order.service;

import com.example.order.entities.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    public Optional<Order> getOrderById(Long id);
    public List<Order> getAllOrders();
    public Order addOrder(Order order);
}
