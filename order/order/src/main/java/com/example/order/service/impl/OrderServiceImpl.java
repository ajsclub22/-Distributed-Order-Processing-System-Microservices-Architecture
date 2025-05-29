package com.example.order.service.impl;

import com.example.order.dto.ProductDTO;
import com.example.order.entities.Order;
import com.example.order.repo.OrderRepository;
import com.example.order.service.OrderService;
import com.example.order.service.ProductValidateService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    //Repository Object
    private final OrderRepository orderRepository;

    //constructor
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    //get Order by id
    //if order not find return null
    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    //get list of all the orders
    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    //save order and return the saved order
    @Override
    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }
}
