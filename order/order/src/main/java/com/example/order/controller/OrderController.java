package com.example.order.controller;


import com.example.order.dto.ProductDTO;
import com.example.order.entities.Order;
import com.example.order.service.OrderService;
import com.example.order.service.ProductValidateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final ProductValidateService productValidateService;

    public OrderController(OrderService orderService, ProductValidateService productValidateService) {
        this.orderService = orderService;
        this.productValidateService = productValidateService;
    }

    @PostMapping
    public ResponseEntity<?> addOrder(@RequestBody Order order) {
        ProductDTO productDTO = productValidateService.getProductById(order.getProductId());

        if (productDTO != null) {
            Order savedOrder = orderService.addOrder(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
        } else {
            String message = String.format(
                    "Product with ID: %d is not available.%nOrder was not placed.%nOrder %s was cancelled.",
                    order.getProductId(), order
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orderList = orderService.getAllOrders();
        return ResponseEntity.ok(orderList); // Return empty list with 200 is fine
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)  // specify type explicitly
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Order not available with ID: " + id));
    }

}
