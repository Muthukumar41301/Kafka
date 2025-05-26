package com.kafka.kafka_integration.controller;

import com.kafka.kafka_integration.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @PostMapping("/message/send")
    public ResponseEntity<String> send(@RequestParam String key,@RequestParam String senderNumber,@RequestParam String body){
        producerService.sendMobileMessage(key,senderNumber,body);
        return ResponseEntity.ok("Message send");
    }

    @GetMapping("/partition/send")
    public String sendToPartition(@RequestParam int partition,
                                  @RequestParam String key,
                                  @RequestParam String message) {
        producerService.sendMessageToPartition(partition, key, message);
        return "Message sent to partition " + partition;
    }

    @PostMapping("/file/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            byte[] fileContent = file.getBytes();
            producerService.uploadFile(fileContent);
            return "File sent to Kafka successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to send file to Kafka: " + e.getMessage();
        }
    }
}
