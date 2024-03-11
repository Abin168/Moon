package com.moon.consumer.config;

import com.moon.consumer.consumer.MoonConsumerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

/**
 * Kafka consumer config
 * we need use @DependsOn before use @Autowired
 */
@Configuration
@SuppressWarnings(value = {"rawtypes"})
@DependsOn(value = {"kafkaConsumerFactory", "moonConsumerListener"})
public class MoonConsumerConfig {

    @Autowired
    private ConsumerFactory consumerFactory;
    @Autowired
    private MoonConsumerListener moonConsumerListener;

    /**
     * moon topic MessageListenerContainer
     *
     * @return
     */
    @Bean
    public ConcurrentMessageListenerContainer messageListenerContainerMoon() {
        return getContainer("moon");
    }

    /**
     * sun topic MessageListenerContainer
     *
     * @return
     */
    @Bean
    public ConcurrentMessageListenerContainer messageListenerContainerSun() {
        return getContainer("sun");
    }

    public ConcurrentMessageListenerContainer getContainer(String topic) {
        ContainerProperties properties = new ContainerProperties(topic);
        properties.setAckMode(ContainerProperties.AckMode.MANUAL);
        properties.setMessageListener(moonConsumerListener);
        ConcurrentMessageListenerContainer container = new ConcurrentMessageListenerContainer(consumerFactory, properties);
        container.start();
        return container;
    }
}
