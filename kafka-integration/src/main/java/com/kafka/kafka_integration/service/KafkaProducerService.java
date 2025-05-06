package com.kafka.kafka_integration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        kafkaTemplate.send("my-topic", message);
        kafkaTemplate.send("my-topic1", message);
        kafkaTemplate.send("my-topic2", message);
        kafkaTemplate.send("my-topic3", message);
    }

}
