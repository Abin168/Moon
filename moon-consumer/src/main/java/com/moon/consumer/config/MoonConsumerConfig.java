package com.moon.consumer.config;

import com.moon.consumer.consumer.MoonConsumer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

@Configuration
public class MoonConsumerConfig {

    @Bean
//    @ConditionalOnBean(value = {ConsumerFactory.class, MoonConsumer.class})
    @Order(value = Integer.MIN_VALUE + 10)
    public ConcurrentMessageListenerContainer messageListenerContainerMoon(ConsumerFactory consumerFactory,
                                                                           @Qualifier("moonConsumer") MoonConsumer moonTopicConsumer) {
        ContainerProperties containerProperties = new ContainerProperties("moon");
        containerProperties.setMessageListener(moonTopicConsumer);
        containerProperties.setAckMode(ContainerProperties.AckMode.MANUAL);
        ConcurrentMessageListenerContainer container = new ConcurrentMessageListenerContainer(consumerFactory, containerProperties);
        container.start();
        return container;
    }
}
