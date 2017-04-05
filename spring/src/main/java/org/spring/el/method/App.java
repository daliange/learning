package org.spring.el.method;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	
	public static void main(String[] args) {
	    ApplicationContext context = new ClassPathXmlApplicationContext("el/method/applicationContext.xml");

	    Customer obj = (Customer) context.getBean("customerBean");
	    System.out.println(obj);
	}

}
