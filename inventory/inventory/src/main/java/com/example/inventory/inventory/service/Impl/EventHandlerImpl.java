package com.example.inventory.inventory.service.Impl;

import com.example.inventory.inventory.config.KafkaConfiguration;
import com.example.inventory.inventory.entities.Inventory;
import com.example.inventory.inventory.enums.InventoryStatus;
import com.example.inventory.inventory.events.InventoryEvent;
import com.example.inventory.inventory.repo.InventoryRepository;
import com.example.inventory.inventory.service.EventHandler;
import com.example.inventory.inventory.service.kafka.InventoryKafkaProducer;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Consumer;


@Service
public class EventHandlerImpl implements EventHandler {

    private final InventoryRepository repo;
    private final InventoryKafkaProducer kafkaProducer;
    private final KafkaConfiguration config;

    public EventHandlerImpl(InventoryRepository repo, InventoryKafkaProducer kafkaProducer, KafkaConfiguration config) {
        this.repo = repo;
        this.kafkaProducer = kafkaProducer;
        this.config = config;
    }

    @Override
    public void processOrderEvent(InventoryEvent event) {

        handleInventoryEvent(event);

    }

    @Override
    public void handleInventoryEvent(InventoryEvent event) {
        InventoryEvent sEvent = new InventoryEvent(event);
        Optional<Inventory> optional = repo.findByProductId(event.getProductId());
        if(optional.isPresent() ){
            Inventory inventory = optional.get();
            if(inventory.getAvailableQuantity() >= event.getQuantity())
            {
                reservedInventory(inventory, event.getQuantity());
                System.out.println("in process");
                setReason(sEvent, "Reserved Stock",InventoryStatus.Reserved);


            }
            else {
                setReason(sEvent, "Out Of Stock",InventoryStatus.Failed );
            }
        }
        else {
            setReason(sEvent, "Product Not Available",InventoryStatus.Failed );
        }
        kafkaProducer.publishMsg(config.getInventory(), sEvent);
    }

    private void setReason(InventoryEvent event, String reason, InventoryStatus status)
    {
        event.setStatus(status);
        event.setReason(reason);
    }

    public void reservedInventory(Inventory invent, int quantity) {
        Consumer<Inventory> consumer = inventory -> {
            inventory.setTotalQuantity(inventory.getTotalQuantity() - quantity);
            inventory.setReservedQuantity(inventory.getReservedQuantity()+ quantity);
            repo.save(inventory);
        };
       consumer.accept(invent);
    }

//    @Override
//    public InventoryEvent getIEvent(InventoryEvent event) {
//        InventoryEvent iEvent = new InventoryEvent();
//        iEvent.setOrderId(event.getOrderId());
//        return iEvent;
//    }


}
