package org.spring.requiredbean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	
	public static void main( String[] args )
    {
    	ApplicationContext context = 
			new ClassPathXmlApplicationContext("requiredbean/applicationContext.xml");

    	Customer cust = (Customer)context.getBean("CustomerBean");
    	System.out.println(cust);
    	
    	
    	
    }

}
