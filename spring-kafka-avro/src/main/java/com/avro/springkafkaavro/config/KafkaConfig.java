package com.avro.springkafkaavro.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@Configurable
public class KafkaConfig {

    @Value("${topic.name}")
    private String topicName;

    @Bean
    public NewTopic newTopic() {
        return new NewTopic(topicName, 3, (short) 1);
    }
}
