package org.springframework.beans.factory;

import org.springframework.beans.BeansException;

public interface BeanFactory {

    Object getBean(String name) throws BeansException;

    <T> T getBean(String name,Class<T> requiredType) throws BeansException;
}
