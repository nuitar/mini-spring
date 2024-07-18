package org.springframework.beans.factory.support;

import org.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    private Map<String, Object> singletonMap = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return singletonMap.get(beanName);
    }

    protected void addSingleton(String beanName, Object obj) {
        singletonMap.put(beanName, obj);
    }
}
