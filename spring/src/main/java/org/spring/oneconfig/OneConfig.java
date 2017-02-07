package org.spring.oneconfig;

import org.spring.helloworld.HahaWorld;
import org.spring.helloworld.HelloWorld;
import org.spring.ioc.test5.Customer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class OneConfig {
	
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext context = 
	    		new ClassPathXmlApplicationContext("oneconfig/Spring-OneConfig.xml");
		
		HelloWorld helloWorld = (HelloWorld) context.getBean("helloBean");
		helloWorld.printHello();
		
		Customer cust = (Customer)context.getBean("CustomerBean");
    	System.out.println(cust);
	}
	
	

}
