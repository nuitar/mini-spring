package org.springframework.test.ioc;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class SimpleBeanContainerTest {
    @Test
    public void testGetBean() {
        BeanFactory factory = new BeanFactory();
        factory.registerBean("helloService", new HelloService());

        HelloService service = (HelloService) factory.getBean("helloService");
        assertThat(service).isNotNull();
        assertThat(service.sayHello()).isEqualTo("hello");
        service.sayHello();
    }

    class HelloService {
        public String sayHello() {
            System.out.println("hello");
            return "hello";
        }
    }
}
