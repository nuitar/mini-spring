package org.springframework.context.annotation;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Set;

public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {
    private BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void doScan(String... basePackages) {
        for (String basePackage : basePackages) {
            Set<BeanDefinition> beanDefinitions = findCandidateComponents(basePackage);
            for (BeanDefinition beanDefinition : beanDefinitions) {
                String scope = getBeanScope(beanDefinition.getBeanClass());
                if (StrUtil.isNotEmpty(scope))
                    beanDefinition.setScope(scope);

                String beanName = getBeanName(beanDefinition.getBeanClass());
                registry.registerBeanDefinition(beanName, beanDefinition);
            }
        }
    }

    private String getBeanName(Class<?> beanClass) {
        Component component = beanClass.getAnnotation(Component.class);
        if (StrUtil.isEmpty(component.value())) {
            return StrUtil.lowerFirst(beanClass.getSimpleName());
        }
        return component.value();
    }

    private String getBeanScope(Class<?> bean) {
        Scope scope = bean.getAnnotation(Scope.class);
        if (scope != null && StrUtil.isNotEmpty(scope.value()))
            return scope.value();
        return StrUtil.EMPTY;
    }

    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    public void setRegistry(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }
}
