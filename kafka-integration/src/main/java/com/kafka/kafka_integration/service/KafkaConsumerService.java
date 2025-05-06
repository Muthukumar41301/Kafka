package com.kafka.kafka_integration.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "my-topic", groupId = "group_id")
    public void listen(String message) {
        System.out.println("Received Message: " + message);
    }

    @KafkaListener(topics = "my-topic1", groupId = "group_id")
    public void listen1(String message) {
        System.out.println("Received Message1: " + message);
    }

    @KafkaListener(topics = "my-topic2", groupId = "group_id")
    public void listen2(String message) {
        System.out.println("Received Message2: " + message);
    }
}
