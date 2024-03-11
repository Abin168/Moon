package com.moon.consumer.consumer;

import com.moon.consumer.executor.ExecutorMapping;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * MoonConsumerListener
 */
@Slf4j
@Component(value = "moonConsumerListener")
public class MoonConsumerListener implements AcknowledgingMessageListener<Integer, String> {

    @Override
    public void onMessage(ConsumerRecord<Integer, String> record, Acknowledgment ack) {
        String topic = record.topic();
        String msgId = String.format("%s-%d-%d", topic, record.partition(), record.offset());
        if ("moon".equals(topic)) {
            dealMessage(msgId, record, ack);
        } else if ("sun".equals(topic)) {
            dealMessage(msgId, record, ack);
        }
    }

    private void dealMessage(String msgId, ConsumerRecord<Integer, String> record, Acknowledgment ack) {
        Integer key = record.key();
        String value = record.value();
        log.info("MoonConsumer dealMessage msgId={} key={} value={}", msgId, key, value);
        boolean result = ExecutorMapping.getInstance().invoke(key, value);
        if (result) {
            if (ack != null) {
                ack.acknowledge();
            }
        }
    }

}
