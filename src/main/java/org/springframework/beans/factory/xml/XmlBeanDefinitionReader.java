package org.springframework.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {
    public static final String ID_ATTRIBUTE = "id";
    public static final String NAME_ATTRIBUTE = "name";
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String PROPERTY_ATTRIBUTE = "property";
    public static final String BEAN_ATTRIBUTE = "bean";
    public static final String CLASS_ATTRIBUTE = "class";
    public static final String REF_ATTRIBUTE = "ref";
    public static final String INIT_METHOD_ATTRIBUTE = "init-method";
    public static final String DESTROY_METHOD_ATTRIBUTE = "destroy-method";
    public static final String SCOPE_ATTRIBUTE = "scope";
    public static final String BASE_PACKAGE_ATTRIBUTE = "base-package";

    public static final String COMPONENT_SCAN_ELEMENT = "component-scan";
    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader loader) {
        super(registry, loader);
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        this.loadBeanDefinitions(resource);
    }

    @Override
    public void loadBeanDefinitions(Resource location) throws BeansException {
        try {
            InputStream inputStream = location.getInputStream();
            try {
                doLoadBeanDefinition(inputStream);
            } finally {
                inputStream.close();
            }
        } catch (Exception ex) {
            throw new BeansException("IOException parsing XML document from " + location, ex);
        }
    }

    private void doLoadBeanDefinition(InputStream inputStream) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        Element root = document.getRootElement();

        Element componentElement = root.element(COMPONENT_SCAN_ELEMENT);
        if(componentElement!=null){
            String scanPath = componentElement.attributeValue(BASE_PACKAGE_ATTRIBUTE);
            if(StrUtil.isEmpty(scanPath))
                throw new BeansException("The value of base-package attribute can not be empty or null");
            scanPackage(scanPath);
        }


        List<Element> beanList = root.elements(BEAN_ATTRIBUTE);
        for (Element bean:beanList) {

            String id = bean.attributeValue(ID_ATTRIBUTE);
            String name = bean.attributeValue(NAME_ATTRIBUTE);
            String className = bean.attributeValue(CLASS_ATTRIBUTE);
            String initMethodName = bean.attributeValue(INIT_METHOD_ATTRIBUTE);
            String destroyMethodName = bean.attributeValue(DESTROY_METHOD_ATTRIBUTE);
            String scope = bean.attributeValue(SCOPE_ATTRIBUTE);

            Class<?> clazz = null;
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                throw new BeansException("Cannot find class [" + className + "]");
            }

            String beanName = StrUtil.isNotEmpty(id) ? id : name;
            if (StrUtil.isEmpty(beanName)) {
                beanName = StrUtil.lowerFirst(clazz.getSimpleName());
            }

            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            beanDefinition.setInitMethodName(initMethodName);
            beanDefinition.setDestroyMethodName(destroyMethodName);
            if (StrUtil.isNotEmpty(scope))
                beanDefinition.setScope(scope);

            List<Element> propertyList = bean.elements(PROPERTY_ATTRIBUTE);
            // 获取proptery
            for (Element property:propertyList) {
                String propertyName = property.attributeValue(NAME_ATTRIBUTE);
                String val = property.attributeValue(VALUE_ATTRIBUTE);
                String refAttribute = property.attributeValue(REF_ATTRIBUTE);

                if (StrUtil.isEmpty(propertyName)) {
                    throw new BeansException("The name attribute cannot be null or empty");
                }

                Object value = val;
                if (StrUtil.isNotEmpty(refAttribute)) {
                    value = new BeanReference(refAttribute);
                }
                PropertyValue propertyValue = new PropertyValue(propertyName, value);

                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }

            if (getRegistry().containsBeanDefinition(beanName))
                throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
            getRegistry().registerBeanDefinition(beanName, beanDefinition);

        }
    }

    private void scanPackage(String scanPath) {
        String[] basePackages = StrUtil.splitToArray(scanPath, ',');

        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(getRegistry());
        scanner.doScan(basePackages);
    }
}
