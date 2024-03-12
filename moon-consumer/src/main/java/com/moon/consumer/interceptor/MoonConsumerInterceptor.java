package com.moon.consumer.interceptor;

import com.moon.core.common.constants.MoonConstants;
import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.MDC;

import java.util.Arrays;
import java.util.Map;

/**
 * MoonConsumerInterceptor
 */
public class MoonConsumerInterceptor implements ConsumerInterceptor<Integer, String> {
    @Override
    public ConsumerRecords<Integer, String> onConsume(ConsumerRecords<Integer, String> records) {
        records.forEach(record -> Arrays.asList(record.headers().toArray()).forEach(header -> {
            if (header.key().equals(MoonConstants.TRACE_ID)) {
                MDC.put(header.key(), new String(header.value()));
            }
        }));
        return records;
    }

    @Override
    public void onCommit(Map<TopicPartition, OffsetAndMetadata> offsets) {

    }

    @Override
    public void close() {
        MDC.remove(MoonConstants.TRACE_ID);
    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
