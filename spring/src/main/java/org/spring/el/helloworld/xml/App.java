package org.spring.el.helloworld.xml;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	
	public static void main(String[] args) {
	    ApplicationContext context = new ClassPathXmlApplicationContext("el/helloworld/xml/applicationContext.xml");

	    Customer obj = (Customer) context.getBean("customerBean");
	    System.out.println(obj);
	}

}
