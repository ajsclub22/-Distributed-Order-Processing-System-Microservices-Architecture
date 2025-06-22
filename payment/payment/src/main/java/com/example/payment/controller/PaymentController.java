package com.example.payment.controller;

import com.example.payment.service.StripeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final StripeService stripeService;

    public PaymentController(StripeService stripeService) {
        this.stripeService = stripeService;
    }
    @GetMapping("/status/{orderId}")
    public ResponseEntity<?> getPaymentStatus(@PathVariable Long orderId)
    {
        String url = stripeService.getSessionUrl(orderId);
        if(url == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment is not ready");
        }
        return ResponseEntity.ok(Map.of("status", "Ready", "paymnetUrl", url));
    }
}
