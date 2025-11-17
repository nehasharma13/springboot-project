package com.ecommerce.ecommerce.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    @KafkaListener(topics = "order-topic", groupId = "ecommerce-group")
    public void consumerOrderEvent(String message) {
        System.out.println("📨 Received Order Event from Kafka -> " + message);
    }
}
