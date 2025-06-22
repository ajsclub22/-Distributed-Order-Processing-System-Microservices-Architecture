package com.example.payment.controller;

import com.example.payment.enums.PaymentStatus;
import com.example.payment.events.PaymentEvent;
import com.example.payment.service.EventHandler;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/webhook")
public class WehhookController {
    @Value("${stripe.webhook.secret}")
    private String webhookKey;

    private final EventHandler handler;

    private final Set<String> processEventId = ConcurrentHashMap.newKeySet();

    public WehhookController(EventHandler handler) {
        this.handler = handler;
    }

    @PostMapping
    public ResponseEntity<String> handleWebhook(@RequestBody String paylod, @RequestHeader("Stripe-Signature") String sigHeader)
    {
        try {
            Event event = Webhook.constructEvent(paylod, sigHeader, webhookKey);

            if(!processEventId.add(event.getId()))
            {
                return ResponseEntity.ok("Event already processed");
            }

            var deserializer = event.getDataObjectDeserializer();

            if (deserializer.getObject().isEmpty()) {
                return ResponseEntity.badRequest().body("unable to deserialize object");
            }

            switch (event.getType()) {
                case "checkout.session.completed", "checkout.session.expired",
                     "checkout.session.async_payment_failed" -> {
                    Session session = (Session) deserializer.getObject().get();
                    Long orderId = Long.parseLong(session.getClientReferenceId());
                    Long clientId = Long.parseLong(session.getMetadata().get("clientId"));
                    double price = session.getAmountTotal() / 100.0; // Stripe amount is in cents


                    // Set status based on event type
                    PaymentStatus status = switch (event.getType()) {
                        case "checkout.session.completed" -> PaymentStatus.SUCCESS;
                        case "checkout.session.expired" -> PaymentStatus.EXPIRED;
                        default -> PaymentStatus.FAILED;
                    };

                    PaymentEvent paymentEvent = new PaymentEvent(orderId, price, clientId, status);
                    handler.handlePaymentEvent(paymentEvent);
                    return ResponseEntity.ok("Webhook Received  " + event.getType());
                }
                default -> {
                    // Ignore other events silently
                    return ResponseEntity.ok("Ignored event: " + event.getType());
                }

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Invalid Webhook");
        }
    }

    private PaymentEvent getPEvent(Long orderId, Long clientId, double price)
    {
        PaymentEvent event = new PaymentEvent();
        event.setOrderId(orderId);
        event.setClientId(clientId);
        event.setPrice(price);
        return event;
    }
    {

    }


}
