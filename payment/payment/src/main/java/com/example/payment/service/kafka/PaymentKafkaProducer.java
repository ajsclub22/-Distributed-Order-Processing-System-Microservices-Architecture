package com.example.payment.service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentKafkaProducer {
    private final ObjectMapper mapper;
    private final KafkaTemplate<String,String> producer;

    public PaymentKafkaProducer(KafkaTemplate<String, String> producer) {
        this.producer = producer;
        this.mapper = new ObjectMapper();
    }
    public void publishMsg(String topic, Object event){
        try{
            String msg = mapper.writeValueAsString(event);
            producer.send(topic, msg);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
}
