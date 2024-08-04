package org.springframework.context.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextAwareProcessor implements ApplicationContextAware, BeanPostProcessor {
    private ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext context) {
        this.applicationContext = context;
    }

    @Override
    public Object postProcessBeforeInitialization(String beanName, Object bean) throws BeansException {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(String beanName, Object bean) throws BeansException {
        return bean;
    }
}
