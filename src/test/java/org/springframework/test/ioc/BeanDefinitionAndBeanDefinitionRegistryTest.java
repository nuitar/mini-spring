package org.springframework.test.ioc;

import org.junit.Test;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.test.ioc.service.HelloService;

import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class BeanDefinitionAndBeanDefinitionRegistryTest {
    @Test
    public void testGetBean() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        BeanDefinition helloDefinition = new BeanDefinition(HelloService.class);

        beanFactory.registerBeanDefinition("helloService", helloDefinition);

        HelloService service = (HelloService) beanFactory.getBean("helloService");
        assertThat(service).isNotNull();
        assertThat(service.sayHello()).isEqualTo("hello");

    }

}
