package org.springframework.beans.factory.support;


import org.springframework.beans.factory.config.BeanDefinition;

/**
 * 注册BeanDefinition
 */
public interface BeanDefinitionRegistry {
    public void registerBeanDefinition(String name, BeanDefinition definition);
}
