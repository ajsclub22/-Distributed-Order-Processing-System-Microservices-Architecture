package com.example.notification.service.impl;

import com.example.notification.enums.OrderStatus;
import com.example.notification.service.EventHandler;
import com.example.notification.events.NotificationEvent;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EventHandlerImpl implements EventHandler {
    private final JavaMailSender sender;

    public EventHandlerImpl(JavaMailSender sender) {
        this.sender = sender;
    }

    @Override
    public void handleNotification(NotificationEvent event) {
        String msg = generateEmailTemplate(event);
        String sub= "Your Order Status";
        sendMail(event.getClientMail(), sub, msg);

    }

    private void sendMail(String clientMail, String sub, String msg) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(clientMail);
        message.setSubject(sub);
        message.setText(msg);
        sender.send(message);
    }

    private String generateEmailTemplate(NotificationEvent event) {
        return switch (event.getStatus()) {
            case OrderStatus.CANCELLED -> String.format("Hi  ankit, your order with  order_id :- %d is being Cancelled.", event.getId());
            case OrderStatus.PLACED -> String.format("Hi ankit, your order  with order_id :- %d has been Placed!",event.getId());
            default -> "Hi ankit, we received your order.";
        };
    }
}
