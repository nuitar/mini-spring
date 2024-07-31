package org.springframework.beans.factory.support;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;

/**
 * 注册BeanDefinition
 */
public interface BeanDefinitionRegistry {
    void registerBeanDefinition(String name, BeanDefinition definition);

    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    boolean containsBeanDefinition(String beanName);
    String[] getBeanDefinitionNames();

}
