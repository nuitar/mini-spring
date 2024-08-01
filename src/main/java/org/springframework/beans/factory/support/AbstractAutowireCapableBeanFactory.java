package org.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.BeanReference;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {
    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();


    /**
     * 创建Bean，并且注入属性
     *
     * @param beanName
     * @param beanDefinition
     * @return
     */
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) {
        return doCreateBean(beanName, beanDefinition);
    }

    protected Object doCreateBean(String beanName, BeanDefinition beanDefinition) {
        Object bean = null;
        try {
            bean = getInstantiationStrategy().instantiate(beanDefinition);
            // 注入属性
            applyBeanPropertyValues(beanName, bean, beanDefinition);
            bean = initializeBean(beanName, bean, beanDefinition);
        } catch (Exception ex) {
            throw new BeansException("Instantiation of bean failed", ex);
        }
        addSingleton(beanName, bean);
        return bean;
    }

    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        Object wrappedBean = applyBeforePostProcessor(beanName, bean);

        invokeInitMethod(beanName, wrappedBean, beanDefinition);

        wrappedBean = applyAfterPostProcessor(beanName, wrappedBean);
        return wrappedBean;
    }

    private Object applyAfterPostProcessor(String beanName, Object bean) {
        Object result = bean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessor()) {
            Object current = beanPostProcessor.postProcessAfterInitialization(beanName, bean);
            if (current == null)
                return result;
            result = current;
        }
        return result;
    }

    private void invokeInitMethod(String beanName, Object bean, BeanDefinition beanDefinition) {
    }

    private Object applyBeforePostProcessor(String beanName, Object bean) {
        Object result = bean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessor()) {
            Object current = beanPostProcessor.postProcessBeforeInitialization(beanName, bean);
            if (current == null)
                return result;
            result = current;
        }
        return result;
    }

    protected void applyBeanPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            for (PropertyValue pv : beanDefinition.getPropertyValues().getPropertyValueList()) {
                String name = pv.getName();
                Object value = pv.getValue();
                if (value instanceof BeanReference) {
                    BeanReference reference = (BeanReference) value;
                    value = getBean(reference.getBeanName());
                }
                BeanUtil.setFieldValue(bean, name, value);
            }
        } catch (Exception ex) {
            throw new BeansException("Error setting property values for bean: " + beanName, ex);
        }

    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }
}
