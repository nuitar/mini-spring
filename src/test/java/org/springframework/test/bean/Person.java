package org.springframework.test.bean;

public class Person {
    private String name;
    private int age;
    private Car car;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void customInitMethod(){
        System.out.println("initialize the car by customInitMethod");
    }
    public void customDestroyMethod(){
        System.out.println("destroy the car by customDestroyMethod");
    }



    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", car=" + car +
                '}';
    }
}
