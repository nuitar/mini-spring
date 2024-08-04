package org.springframework.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;

public class DisposableBeanAdapter implements DisposableBean {
    private String beanName;
    private Object bean;
    private String destroyMethodName;

    DisposableBeanAdapter(String beanName, Object bean, BeanDefinition beanDefinition) {
        this.beanName = beanName;
        this.bean = bean;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    @Override
    public void destroy() throws Exception {
        if (bean instanceof DisposableBean) {
            ((DisposableBean) bean).destroy();
        } else {
            //尝试直接执行bean方法
            if (StrUtil.isNotEmpty(destroyMethodName)) {
                try {
                    Method method = bean.getClass().getMethod(destroyMethodName);
                    method.invoke(bean);

                } catch (Exception ex) {
                    throw new BeansException("Couldn't find a destroy method named '" + destroyMethodName + "' on bean with name '" + beanName + "'");
                }

            }
        }
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }
}
