package com.moon.kafka.Interceptor;

import com.moon.kafka.common.Constants;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

public class MoonProducerInterceptor implements ProducerInterceptor<Integer, String> {
    @Override
    public ProducerRecord<Integer, String> onSend(ProducerRecord<Integer, String> record) {
        String traceId = MDC.get(Constants.TRACE_ID);
        String kafkaTraceId = StringUtils.isEmpty(traceId) ? UUID.randomUUID().toString().replace("-", "") : traceId;
        record.headers().add(Constants.TRACE_ID, kafkaTraceId.getBytes(StandardCharsets.UTF_8));
        record.headers().add(Constants.TIME_STAMP, String.valueOf(System.currentTimeMillis()).getBytes(StandardCharsets.UTF_8));
        return record;
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {

    }

    @Override
    public void close() {
        MDC.remove(Constants.TRACE_ID);
    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
