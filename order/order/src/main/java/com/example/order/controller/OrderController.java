package com.example.order.controller;


import com.example.order.dto.OrderDTO;
import com.example.order.entities.Order;
import com.example.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> addOrder(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.addOrder(order));

    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders()); // Return empty list with 200 is fine
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable("id") Long id) {
        OrderDTO order =  orderService.getOrderById(id);
        if(order != null)
            return ResponseEntity.ok(order);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not available with ID: " + id);

    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getOrderByProductId(@PathVariable("productId") Long id) {
        List<OrderDTO> order =  orderService.getOrderByProductId(id);
        if(order != null)
            return ResponseEntity.ok(order);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not available with ID: " + id);

    }

}
