package org.springframework.beans.factory.support;


import org.springframework.beans.factory.config.BeanDefinition;

/**
 * 注册BeanDefinition
 */
public interface BeanDefinitionRegistry {
    public void registryBeanDefinition(String name, BeanDefinition definition);
}
