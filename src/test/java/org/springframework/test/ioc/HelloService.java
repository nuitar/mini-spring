package org.springframework.test.ioc;

class HelloService {
    public String sayHello() {
        System.out.println("hello");
        return "hello";
    }
}