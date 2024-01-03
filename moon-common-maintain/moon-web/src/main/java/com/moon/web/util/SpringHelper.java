package com.moon.web.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public final class SpringHelper implements BeanFactoryPostProcessor {
    private static ConfigurableListableBeanFactory beanFactory;
    private SpringHelper() {}

    @SuppressWarnings("NullableProblems")
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        SpringHelper.beanFactory = beanFactory;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<?> clazz) throws BeansException {
        return beanFactory.getBean((Class<T>) clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String clazz) throws BeansException {
        return (T) beanFactory.getBean(clazz);
    }

    public static Object getBeanByName(String beanName) throws BeansException {
        return beanFactory.getBean(beanName);
    }
}
