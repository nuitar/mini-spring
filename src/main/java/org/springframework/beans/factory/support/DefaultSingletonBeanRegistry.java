package org.springframework.beans.factory.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.SingletonBeanRegistry;

import java.beans.Beans;
import java.util.HashMap;
import java.util.Map;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    private Map<String, Object> singletonMap = new HashMap<>();

    private final Map<String, DisposableBean> disposableBeans = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return singletonMap.get(beanName);
    }

    protected void addSingleton(String beanName, Object obj) {
        singletonMap.put(beanName, obj);
    }

    public void registerDisposableBean(String beanName, DisposableBean bean) {
        disposableBeans.put(beanName, bean);
    }

    public void destroySingletons() {
        String[] beanNames = disposableBeans.keySet().toArray(new String[0]);
        for (String beanName : beanNames) {
            DisposableBean bean = disposableBeans.remove(beanName);
            if (bean != null) {
                try {
                    bean.destroy();
                } catch (Exception e) {
                    throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
                }
            }
        }
    }
}
