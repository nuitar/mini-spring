package org.springframework.beans.factory.config;

import org.springframework.beans.BeansException;

public interface BeanPostProcessor {
    Object postProcessBeforeInitialization(String beanName,Object bean) throws BeansException;

    Object postProcessAfterInitialization(String beanName,Object bean) throws BeansException;
}
