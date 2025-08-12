package com.kafka.kafka_integration.service;

import com.kafka.kafka_integration.model.MessageDto;
import com.kafka.kafka_integration.model.User;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${topic.name}")
    private String topicName;

    public void sendMessage(String message) {
        kafkaTemplate.send(new ProducerRecord<>("my-topic", 0, "key1", message + " to my-topic partition 0"));
        kafkaTemplate.send(new ProducerRecord<>("my-topic1", 0, "key2", message + " to my-topic1 partition 1"));
        kafkaTemplate.send(new ProducerRecord<>("my-topic2", 0, "key3", message + " to my-topic2 partition 0"));
    }

    public void sendMessagesWithHeader(List<String> messages) {
        int partition = 0;
        for (int i = 0; i < messages.size(); i++) {
            String msg = messages.get(i);
            String key = "key-" + i;
            ProducerRecord<String, Object> record = new ProducerRecord<>("testing", partition, key, msg);
            record.headers().add(new RecordHeader("source", "rest-api".getBytes(StandardCharsets.UTF_8)));
            kafkaTemplate.send(record);
        }
    }

    public void sendMobileMessage(String key, String senderNumber, String body) {
        MessageDto message = new MessageDto();
        message.setBody(body);
        message.setMobileNumber(senderNumber);
        kafkaTemplate.send("message",key,message);
    }

    public void sendMessageToPartition(int partition, String key, Object message) {
        CompletableFuture<SendResult<String, Object>> future =
                kafkaTemplate.send("my-topic", partition, key, message);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                RecordMetadata metadata = result.getRecordMetadata();
                System.out.println("Sent message=[" + message +
                        "] to partition=[" + metadata.partition() +
                        "], offset=[" + metadata.offset() + "]");
            } else {
                System.err.println("Failed to send message=[" + message + "] due to: " + ex.getMessage());
            }
        });
    }

    public void uploadFile(byte[] fileContent) {
        kafkaTemplate.send("file",fileContent);
    }

    public void sendEvents(User user) {

        try {
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topicName, user);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    System.out.println("Sent message=[" + user.toString() +
                            "] with offset=[" + result.getRecordMetadata().offset() + "]");
                } else {
                    System.out.println("Unable to send message=[" +
                            user.toString() + "] due to : " + ex.getMessage());
                }
            });
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
