package org.springframework.beans.factory.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {
    @Override
    protected Object createBean(String beanName,BeanDefinition beanDefinition) {
        return doCreateBean(beanName,beanDefinition);
    }

    protected Object doCreateBean(String beanName, BeanDefinition beanDefinition) {
        Object bean = null;
        try {
            Class beanClass = beanDefinition.getBeanClass();
            bean = beanClass.getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            throw new BeansException("Instantiation of bean failed", ex);
        }
        addSingleton(beanName, bean);
        return bean;
    }
}
