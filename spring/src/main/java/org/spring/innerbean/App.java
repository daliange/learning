package org.spring.innerbean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext context1 = new ClassPathXmlApplicationContext("innerbean/Spring-Customer1.xml");
		Customer cust1 = (Customer) context1.getBean("CustomerBean");
		System.out.println(cust1);
		
		@SuppressWarnings("resource")
		ApplicationContext context2 = new ClassPathXmlApplicationContext("innerbean/Spring-Customer2.xml");
		Customer cust2 = (Customer) context2.getBean("CustomerBean");
		System.out.println(cust2);
		
		@SuppressWarnings("resource")
		ApplicationContext context3 = new ClassPathXmlApplicationContext("innerbean/Spring-Customer3.xml");
		Customer cust3 = (Customer) context3.getBean("CustomerBean");
		System.out.println(cust3);
	}

}
