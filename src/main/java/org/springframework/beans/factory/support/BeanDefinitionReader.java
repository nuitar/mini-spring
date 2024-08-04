package org.springframework.beans.factory.support;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public interface BeanDefinitionReader {
    ResourceLoader getResourceLoader();
    BeanDefinitionRegistry getRegistry();

    void loadBeanDefinitions(String location);
    void loadBeanDefinitions(Resource location);
    void loadBeanDefinitions(String[] location);
}
