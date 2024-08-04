package org.springframework.beans.factory.support;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {
    private BeanDefinitionRegistry registry;
    private ResourceLoader resourceLoader;

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry){
        this(registry,new DefaultResourceLoader());
    }

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader loader) {
        this.registry = registry;
        this.resourceLoader = loader;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    @Override
    public void loadBeanDefinitions(String[] locations) {
        for (String location : locations)
            this.loadBeanDefinitions(location);
    }

    public void setResourceLoader(ResourceLoader loader) {
        this.resourceLoader = loader;
    }

    public void setRegistry(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }
}
