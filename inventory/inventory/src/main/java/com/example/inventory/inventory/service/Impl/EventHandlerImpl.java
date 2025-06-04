package com.example.inventory.inventory.service.Impl;

import com.example.inventory.inventory.config.KafkaConfiguration;
import com.example.inventory.inventory.dto.InventoryDTO;
import com.example.inventory.inventory.enums.InventoryStatus;
import com.example.inventory.inventory.events.InventoryEvent;
import com.example.inventory.inventory.events.OrderEvent;
import com.example.inventory.inventory.service.EventHandler;
import com.example.inventory.inventory.service.kafka.InventoryKafkaProducer;
import com.example.inventory.inventory.service.InventoryService;
import com.example.inventory.inventory.service.Messenger;
import org.springframework.stereotype.Service;


@Service
public class EventHandlerImpl implements EventHandler {

    private final InventoryService service;
    private final InventoryKafkaProducer kafkaProducer;
    private final KafkaConfiguration config;

    public EventHandlerImpl(InventoryService service, InventoryKafkaProducer kafkaProducer, KafkaConfiguration config) {
        this.service = service;
        this.kafkaProducer = kafkaProducer;
        this.config = config;
    }

    @Override
    public void processOrderEvent(OrderEvent event) {
        OrderEvent sEvent = new OrderEvent();
        sEvent.setOrderId(event.getOrderId());
        InventoryDTO inventoryDTO = service.getInventoryByProductId(event.getProductId());
        if(inventoryDTO != null ){
            if(inventoryDTO.getAvailableQuantity() >= event.getQuantity())
            {
                service.reservedInventory(event.getProductId(), event.getQuantity());
                System.out.println("in process");
                sEvent.setReason("Reservered Stock");
                sEvent.setStatus(InventoryStatus.Reserved);

            }
            else {
                sEvent.setReason("Out Of Stock");
                sEvent.setStatus(InventoryStatus.Failed);
            }
        }
        else {
            sEvent.setReason("Product Not Available");
            sEvent.setStatus(InventoryStatus.Failed);

        }
        kafkaProducer.publishMsg(config.getInventory(), sEvent);
    }

    @Override
    public InventoryEvent getIEvent(OrderEvent event) {
        InventoryEvent iEvent = new InventoryEvent();
        iEvent.setOrderId(event.getOrderId());
        return iEvent;
    }


}
