package com.example.notification.service.kafka;

import com.example.notification.service.EventHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.notification.events.NotificationEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class NotificationKafkaConsumer {
    ObjectMapper mapper = new ObjectMapper();
    private final EventHandler hanlder;

    public NotificationKafkaConsumer(EventHandler hanlder) {
        this.hanlder = hanlder;
    }

    @KafkaListener(topics = "${kafka.topics.notify.request}", groupId = "${kafka.consumer.group.id}")
    public void processNotifyEvent(String msg)
    {
        try {
            NotificationEvent event = mapper.readValue(msg, NotificationEvent.class);
            hanlder.handleNotification(event);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
