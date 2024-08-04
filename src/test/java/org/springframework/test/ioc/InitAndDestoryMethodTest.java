package org.springframework.test.ioc;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class InitAndDestoryMethodTest {
    @Test
    public void testInitAndDestory(){
        ClassPathXmlApplicationContext beanFactory = new ClassPathXmlApplicationContext("classpath:init-and-destroy-method.xml");
        beanFactory.close();

    }
}
