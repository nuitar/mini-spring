package org.springframework.test.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class Car implements InitializingBean, DisposableBean {
    private String brand;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                '}';
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("destroy the car");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("initialize the car");
    }
}
