package org.springframework.test.ioc;

import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class SimpleBeanContainerTest {
    @Test
    public void testGetBean() {

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        BeanDefinition helloDefinition = new BeanDefinition(HelloService.class);

        beanFactory.registryBeanDefinition("helloService", helloDefinition);

        HelloService service = (HelloService) beanFactory.getBean("helloService");
        assertThat(service).isNotNull();
        assertThat(service.sayHello()).isEqualTo("hello");
        service.sayHello();
    }

}
