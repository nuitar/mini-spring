package org.springframework.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {
    private static final String ID_ATTRIBUTE = "id";
    private static final String NAME_ATTRIBUTE = "name";
    private static final String VALUE_ATTRIBUTE = "value";
    private static final String PROPERTY_ATTRIBUTE = "property";
    private static final String BEAN_ATTRIBUTE = "bean";
    private static final String CLASS_ATTRIBUTE = "class";
    public static final String REF_ATTRIBUTE = "ref";
    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry){
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
        } catch (IOException ex) {
            throw new BeansException("IOException parsing XML document from " + location, ex);
        }
    }

    private void doLoadBeanDefinition(InputStream inputStream) {
        Document document = XmlUtil.readXML(inputStream);
        Element root = document.getDocumentElement();
        NodeList childNodes = root.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            if (!(childNodes.item(i) instanceof Element))
                continue;

            Element bean = (Element) childNodes.item(i);
            String id = bean.getAttribute(ID_ATTRIBUTE);
            String name = bean.getAttribute(NAME_ATTRIBUTE);
            String className = bean.getAttribute(CLASS_ATTRIBUTE);

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

            // 获取proptery
            for (int j = 0; j < bean.getChildNodes().getLength(); j++) {
                if (!(bean.getChildNodes().item(j) instanceof Element)) {
                    continue;
                }

                Element property = (Element) bean.getChildNodes().item(j);
                String propertyName = property.getAttribute(NAME_ATTRIBUTE);
                String val = property.getAttribute(VALUE_ATTRIBUTE);
                String refAttribute = property.getAttribute(REF_ATTRIBUTE);

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
}
