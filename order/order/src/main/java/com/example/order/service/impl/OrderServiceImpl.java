package com.example.order.service.impl;

import com.example.order.dto.OrderDTO;
import com.example.order.entities.Order;
import com.example.order.service.EventHandler;
import com.example.order.repo.OrderRepository;
import com.example.order.service.OrderService;
import com.example.order.util.OrderUtility;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    //Repository Object
    private final OrderRepository orderRepository;
    private final EventHandler handler;

    public OrderServiceImpl(OrderRepository orderRepository, EventHandler handler) {
        this.orderRepository = orderRepository;
        this.handler = handler;
    }


    //get Order by id
    //if order not find return null
    @Override
    public OrderDTO getOrderById(Long id) {

        return orderRepository.findById(id)
                        .map(OrderUtility::getOrderDTO)
                .orElse(null);
    }

    //get list of all the orders
    @Override
    public List<OrderDTO> getAllOrders() {

        return mapOrdersToDTOs(orderRepository.findAll());
    }

    //save order and return the saved order
    @Override
    public OrderDTO addOrder(Order order) {

        //save the order
        Order savedOrder = orderRepository.save(order);
        //send the event to the inventory for reserved inventory
        handler.handleInventoryEvent(savedOrder);

        //return back the order details to the client
        return OrderUtility.getOrderDTO(savedOrder);

    }

    @Override
    public List<OrderDTO> getOrderByProductId(Long id) {
        return mapOrdersToDTOs(orderRepository.findByProductId(id));
    }






    private List<OrderDTO> mapOrdersToDTOs(List<Order> orderList)
    {
        return orderList.
                stream().map(OrderUtility::getOrderDTO)
                .collect(Collectors.toList());
    }


}
