package org.springframework.beans.factory;

import org.springframework.beans.BeansException;

public interface BeanFactory {

    public Object getBean(String name) throws BeansException;
}
