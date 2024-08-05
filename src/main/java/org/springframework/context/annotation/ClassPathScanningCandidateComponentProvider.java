package org.springframework.context.annotation;

import cn.hutool.core.util.ClassUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

public class ClassPathScanningCandidateComponentProvider {
    public Set<BeanDefinition> findCandidateComponents(String basePackage){
        Set<BeanDefinition>result = new LinkedHashSet<>();
        Set<Class<?>> beans = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for(Class bean:beans){
            BeanDefinition beanDefinition = new BeanDefinition(bean);
            result.add(beanDefinition);
        }
        return  result;
    }
}
