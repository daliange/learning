package org.spring.listfactorybean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	public static void main(String[] args) {
		
		@SuppressWarnings("resource")
		ApplicationContext context1 = new ClassPathXmlApplicationContext(
				"listfactorybean/applicationContextOne.xml");
		Customer cust1 = (Customer) context1.getBean("CustomerBean");
		System.out.println(cust1);
		
		@SuppressWarnings("resource")
		ApplicationContext context2 = new ClassPathXmlApplicationContext(
				"listfactorybean/applicationContextTwo.xml");
		Customer cust2 = (Customer) context2.getBean("CustomerBean");
		System.out.println(cust2);
		
	}
}