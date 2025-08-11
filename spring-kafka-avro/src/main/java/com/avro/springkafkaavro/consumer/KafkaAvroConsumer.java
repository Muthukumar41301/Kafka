package com.avro.springkafkaavro.consumer;

import com.avro.springkafkaavro.dto.Employee;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class KafkaAvroConsumer {

    @KafkaListener(topics = "${topic.name}")
    public void read(ConsumerRecord<String, Employee> record) {
        String key = record.key();
        Employee employee = record.value();
        log.info("Avro message received for key : {} value : {}", key, employee.toString());
    }
}
