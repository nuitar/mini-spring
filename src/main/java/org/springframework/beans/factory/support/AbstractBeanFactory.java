package org.springframework.beans.factory.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {
    /**
     * 如果当前没有创建bean实例，则会创建
     * @param name
     * @return
     * @throws BeansException
     */
    @Override
    public Object getBean(String name) throws BeansException {
        Object bean = getSingleton(name);
        if (bean != null)
            return bean;
        BeanDefinition definition = getBeanDefinition(name);
        return createBean(name,definition);
    }

    protected abstract Object createBean(String beanName,BeanDefinition beanDefinition);

    protected abstract BeanDefinition getBeanDefinition(String beanName);
}
