package com.example.order.service.impl;

import com.example.order.config.KafkaConfig;
import com.example.order.entities.Order;
import com.example.order.enums.InventoryStatus;
import com.example.order.enums.OrderStatus;
import com.example.order.enums.PaymentStatus;
import com.example.order.events.InventoryEvent;
import com.example.order.events.NotificationEvent;
import com.example.order.events.PaymentEvent;
import com.example.order.repo.OrderRepository;
import com.example.order.service.EventHandler;
import com.example.order.service.kafka.OrderKakfaProducer;
import com.example.order.util.OrderUtility;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventHandlerImpl implements EventHandler {
    private final OrderKakfaProducer producer;
    private final KafkaConfig config;
    private final OrderRepository repo;

    public EventHandlerImpl(OrderKakfaProducer producer, KafkaConfig config, OrderRepository repo) {
        this.producer = producer;
        this.config = config;
        this.repo = repo;
    }

    @Override
    public void processInventoryEvent(InventoryEvent event) {
        if(event.getStatus() == InventoryStatus.Reserved) {
            //sending payment event to the payment service
            //step 1 : get the Order from the db
            //step 2 : change the order status as processing
            //step 3 : send the payment event to the paymnet service


            Optional<Order> optional = repo.findById(event.getOrderId());
            if (optional.isPresent()) {
                Order order = optional.get();
                order.setStatus(OrderStatus.PROCESSING);
                repo.save(order);
                handlePaymentEvent(order);
                return;
            }
        }

        //else notify the user that order is called
    }





    @Override
    public void processPaymentEvent(PaymentEvent pEvent) {
        //get the payment event
        //step 1 : get the Order from the db
        //step 2 : change the order status as cancelled or placed
        //step 3 : notify the client
        Optional<Order> optional = repo.findById(pEvent.getOrderId());
        if(optional.isPresent()){
            Order order = optional.get();
            if(pEvent.getStatus() == PaymentStatus.SUCCESS) {
                order.setStatus(OrderStatus.PLACED);
                handleNotifiactionEvent(order);
            }
            else {
                order.setStatus(OrderStatus.CANCELLED);
                //notify the client
            }
            repo.save(order);
        }

        System.out.println("order placed");


    }

    @Override
    public void handleNotifiactionEvent(Order order) {
        NotificationEvent nEvent = OrderUtility.getNotificationEvent(order);
        publishMsg(nEvent);
    }

    @Override
    public void handleInventoryEvent(Order order) {
        //handle the inventory send event & send the event
        InventoryEvent iEvent = OrderUtility.getInventoryEvent(order);
        publishMsg(iEvent);
    }


    @Override
    public void handlePaymentEvent(Order order) {
        // make the payment event and send the event
        //to the payment service
        PaymentEvent event = OrderUtility.getPaymentEvent(order);
        publishMsg(event);
    }





    private void publishMsg(Object event) {
        //method for identified the event type and send
        //the appropriate event to the particular service
        String topic = null;
        if(event instanceof InventoryEvent)
            topic = config.getOrder();
        else if (event instanceof PaymentEvent)
            topic = config.getPayment();
        else if(event instanceof NotificationEvent)
            topic = config.getNotify();


        if(topic != null)
            producer.publishMsg(topic, event);
    }
}
