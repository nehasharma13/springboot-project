package com.ecommerce.ecommerce.kafka;


import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate=kafkaTemplate;
    }

    public void sendOrderMessage(String message) {
        kafkaTemplate.send("order-topic", message);
        System.out.println("📤 Published Order Event to Kafka -> " + message);
    }
}
