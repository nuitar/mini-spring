package org.springframework.test.ioc;

import org.junit.Test;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanReference;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.test.ioc.bean.Car;
import org.springframework.test.ioc.bean.Person;

import java.util.concurrent.Callable;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author derekyi
 * @date 2020/11/24
 */
public class PopulateBeanWithPropertyValuesTest {

	@Test
	public void testPopulateBeanWithPropertyValues() throws Exception {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		PropertyValues propertyValuesForCar = new PropertyValues();
		propertyValuesForCar.addPropertyValue(new PropertyValue("name", "derek"));
		propertyValuesForCar.addPropertyValue(new PropertyValue("age", 18));
		BeanDefinition beanDefinition = new BeanDefinition(Person.class, propertyValuesForCar);
		beanFactory.registerBeanDefinition("car", beanDefinition);

		PropertyValues propertyValuesForPerson = new PropertyValues();
		propertyValuesForPerson.addPropertyValue(new PropertyValue("car",new BeanReference("car")));
		propertyValuesForPerson.addPropertyValue(new PropertyValue("name","李四"));
		propertyValuesForPerson.addPropertyValue(new PropertyValue("age","17"));
		beanFactory.registerBeanDefinition("person",new BeanDefinition(Person.class,propertyValuesForPerson));

		Person person = (Person) beanFactory.getBean("person");

		Car car = (Car) person.getCar();
		System.out.println(car);
		assertThat(car.getName()).isEqualTo("derek");
		assertThat(car.getAge()).isEqualTo(18);
	}
}
