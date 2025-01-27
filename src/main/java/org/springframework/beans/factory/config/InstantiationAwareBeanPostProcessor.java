package org.springframework.beans.factory.config;

import org.springframework.beans.BeansException;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {
    Object postProcessBeforeInitialization(Class<?> beanClass, String beanName) throws BeansException;
}
