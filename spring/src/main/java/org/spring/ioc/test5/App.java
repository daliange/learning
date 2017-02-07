package org.spring.ioc.test5;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	
	 public static void main( String[] args )
	    {
	    	@SuppressWarnings("resource")
			ApplicationContext context = 
	    	  new ClassPathXmlApplicationContext("ioc/test5/Spring-Customer.xml");

	    	Customer cust = (Customer)context.getBean("CustomerBean");
	    	System.out.println(cust);
	    }

}
