package com.kafka.kafka_integration.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.kafka_integration.model.MessageDto;
import com.kafka.kafka_integration.model.User;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.*;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Log4j2
public class KafkaConsumerService {

    @KafkaListener(
            id = "customListener",
            topicPartitions = {
                    @TopicPartition(topic = "my-topic", partitions = { "0" }),
                    @TopicPartition(topic = "my-topic1", partitions = { "0" }),
                    @TopicPartition(topic = "my-topic2", partitions = { "0" })
            },
            groupId = "group_id"
    )
    public void listen(ConsumerRecord<String, String> record) {
        System.out.println("Topic: " + record.topic());
        System.out.println("Partition: " + record.partition());
        System.out.println("Offset: " + record.offset());
        System.out.println("Message: " + record.value());
    }

    @KafkaListener(topics = "testing", groupId = "group_id")
    public void listen(@Payload String message,
                       @Header(name = "source", required = false) String source,
                       @Header(name = KafkaHeaders.TOPIC, required = false) String topic,
                       @Header(name = KafkaHeaders.KEY, required = false) String key,
                       @Header(name = KafkaHeaders.RECEIVED_PARTITION ,required = false) int partition,
                       @Header(name = KafkaHeaders.RECEIVED_TIMESTAMP, required = false) Long timestamp) {
        System.out.println("Received Message: " + message);
        System.out.println("Header 'source': " + source);
        System.out.println("Topic: " + topic);
        System.out.println("Key: " + key);
        System.out.println("partition: " + partition);
        System.out.println("Timestamp: " + timestamp);
    }

    @KafkaListener(topics = "message", groupId = "group_id")
    public void listenMessage(ConsumerRecord<String, MessageDto> record) {
        String key = record.key();
        MessageDto message = record.value();

        System.out.println("Key: " + key);
        System.out.println("Mobile Number: " + message.getMobileNumber());
        System.out.println("Body: " + message.getBody());
    }

    @KafkaListener(topics = "my-topic", groupId = "group_id")
    public void consume(ConsumerRecord<String, String> record) {
        System.out.println("Received message: " + record.value() +
                " from partition: " + record.partition() +
                ", key: " + record.key());
    }


    @RetryableTopic(attempts = "4")// 3 topic N-1
    @KafkaListener(topics = "${topic.name}", groupId = "javatechie-group")
    public void consumeEvents(User user, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) long offset) {
        try {
            log.info("Received: {} from {} offset {}", new ObjectMapper().writeValueAsString(user), topic, offset);
            //validate restricted IP before process the records
            List<String> restrictedIpList = Stream.of("32.241.244.236", "15.55.49.164", "81.1.95.253", "126.130.43.183").toList();
            if (restrictedIpList.contains(user.getIpAddress())) {
                throw new RuntimeException("Invalid IP Address received !");
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @DltHandler
    public void listenDLT(User user, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) long offset) {
        log.info("DLT Received : {} , from {} , offset {}",user.getFirstName(),topic,offset);
    }
}
