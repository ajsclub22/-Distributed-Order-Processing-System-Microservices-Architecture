package com.example.order.service;

import com.example.order.dto.OrderDTO;
import com.example.order.entities.Order;

import java.util.List;

public interface OrderService {

    public OrderDTO getOrderById(Long id);
    public List<OrderDTO> getAllOrders();
    public OrderDTO addOrder(Order order);

    List<OrderDTO> getOrderByProductId(Long id);
}
