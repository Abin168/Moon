package com.moon.consumer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component(value = "moonConsumer")
public class MoonConsumer implements AcknowledgingMessageListener<Integer, String> {

    @Override
    public void onMessage(ConsumerRecord<Integer, String> record, Acknowledgment ack) {
        String topic = record.topic();
        String msgId = String.format("%s-%d-%d", topic, record.partition(), record.offset());
        Integer key = record.key();
        String value = record.value();
        if ("moon".equals(topic)) {
            log.info("MoonConsumer onMessage msgId={} key={} value={}", msgId, key, value);
        }
        ack.acknowledge();
    }
}
