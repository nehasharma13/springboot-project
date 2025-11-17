package com.ecommerce.ecommerce.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka")
public class MessageController {

    @Autowired
    private MessageProducer messageProducer;

    @PostMapping("/publish")
    public String publishMessage(@RequestParam("message") String message) {
        messageProducer.sendMessage(message);
        return "✅ Message published to Kafka topic successfully!";

    }
}
