package com.kafka.kafka_integration.controller;

import com.kafka.kafka_integration.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    private KafkaProducerService producerService;

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestParam String msg) {
        producerService.sendMessage(msg);
        return ResponseEntity.ok("Message sent");
    }

    @PostMapping("/V1/send")
    public ResponseEntity<String> send(@RequestBody List<String> messages) {
        producerService.sendMessagesWithHeader(messages);
        return ResponseEntity.ok("Batch sent");
    }

}
