package com.moon.kafka.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
public class KafkaUtil {

    private static KafkaTemplate kafkaTemplate;

    @Autowired
    @SuppressWarnings(value = {"rawtypes"})
    public void setRedisTemplate(@Qualifier("kafkaTemplate") KafkaTemplate kafkaTemplate) {
        KafkaUtil.kafkaTemplate = kafkaTemplate;
    }

    @SuppressWarnings(value = {"rawtypes"})
    private static void sendToKafka(String topic, Integer key, String value) {
        ListenableFuture rst = kafkaTemplate.send(topic, key, value);
    }
}
