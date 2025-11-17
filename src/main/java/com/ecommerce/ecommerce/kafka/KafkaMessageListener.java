package com.ecommerce.ecommerce.kafka;

import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageListener {

    @KafkaListener(topics = "order-topic", groupId = "ecommerce-group")
    public void consumeMessage(String message) {
        System.out.println("📩 Received message: " + message);
    }
}
