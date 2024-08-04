package org.springframework.test.common;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.test.bean.Car;

public class CustomBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(String beanName, Object bean) throws BeansException {
        if("car".equals(beanName))
            ((Car)bean).setBrand("BMW");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(String beanName, Object bean) throws BeansException {
        System.out.println("after initialization");
        return bean;
    }
}
