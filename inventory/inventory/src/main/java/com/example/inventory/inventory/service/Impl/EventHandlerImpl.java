package com.example.inventory.inventory.service.Impl;

import com.example.inventory.inventory.config.KafkaConfig;
import com.example.inventory.inventory.entities.Inventory;
import com.example.inventory.inventory.entities.InventoryReservation;
import com.example.inventory.inventory.enums.InventoryStatus;
import com.example.inventory.inventory.events.InventoryEvent;
import com.example.inventory.inventory.repo.InventoryRepository;
import com.example.inventory.inventory.repo.InventoryReservationRepository;
import com.example.inventory.inventory.service.EventHandler;
import com.example.inventory.inventory.service.kafka.InventoryKafkaProducer;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.BiConsumer;


@Service
public class EventHandlerImpl implements EventHandler {

    private final InventoryRepository repo;
    private final InventoryKafkaProducer producer;
    private final KafkaConfig config;
    private final InventoryReservationRepository reserveRepo;

    public EventHandlerImpl(InventoryRepository repo, InventoryKafkaProducer kafkaProducer, KafkaConfig config, InventoryReservationRepository reserveRepo) {
        this.repo = repo;
        this.producer = kafkaProducer;
        this.config = config;
        this.reserveRepo = reserveRepo;
    }

    @Override
    public void processOrderEvent(InventoryEvent event) {

        handleInventoryEvent(event);

    }

    @Override
    public void handleInventoryEvent(InventoryEvent event) {
        InventoryEvent sEvent = new InventoryEvent(event);

        //get the inventory by order id
        //if the order id is already present please not to take action
        //because the kafka can receive multiple message for same order if also
        boolean vis = reserveRepo.existsById(event.getOrderId());
        if(vis){
            //return not to anything
            return ;
        }

        //order id is not present
        //process the order
        Optional<Inventory> optional = repo.findByProductId(event.getProductId());
        if(optional.isPresent()){
            Inventory inventory = optional.get();
            if(inventory.getAvailableQuantity() >= event.getQuantity())
            {
                reservedInventory(inventory, event);
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
        publishMsg(sEvent);
    }

    private void setReason(InventoryEvent event, String reason, InventoryStatus status)
    {
        event.setStatus(status);
        event.setReason(reason);
    }

    public void reservedInventory(Inventory inventory, InventoryEvent iEvent) {


        BiConsumer<Inventory, Integer> consumer = (invent, quantity) -> {
            invent.setTotalQuantity(invent.getTotalQuantity() - quantity);
            invent.setReservedQuantity(invent.getReservedQuantity() + quantity);
            repo.save(invent);
        };
        consumer.accept(inventory, iEvent.getQuantity());

        //reserve the inventory in reservation
        InventoryReservation inReserv =getInvenReserv(iEvent);
        reserveRepo.save(inReserv);

    }

    public InventoryReservation getInvenReserv(InventoryEvent iEvent)
    {
        InventoryReservation inReserv =new InventoryReservation();
        inReserv.setOrderId(iEvent.getOrderId());

        inReserv.setProductId(iEvent.getProductId());
        inReserv.setQuantity(iEvent.getQuantity());
        inReserv.setStatus(InventoryStatus.Reserved);
        return inReserv;
    }

    private void publishMsg(Object event) {
        //method for identified the event type and send
        //the appropriate event to the particular service
        String topic = null;
        if(event instanceof InventoryEvent)
            topic = config.getInventory().getResponse();


        if(topic != null)
            producer.publishMsg(topic, event);
    }

//    @Override
//    public InventoryEvent getIEvent(InventoryEvent event) {
//        InventoryEvent iEvent = new InventoryEvent();
//        iEvent.setOrderId(event.getOrderId());
//        return iEvent;
//    }


}
