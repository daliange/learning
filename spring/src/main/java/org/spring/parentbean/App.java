package org.spring.parentbean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	
	public static void main( String[] args )
    {
    	ApplicationContext context1 = 
			new ClassPathXmlApplicationContext("parentbean/applicationContext1.xml");

    	Customer cust1 = (Customer)context1.getBean("CustomerBean");
    	System.out.println(cust1);
    	
    	
    	ApplicationContext context2 = 
			new ClassPathXmlApplicationContext("parentbean/applicationContext2.xml");

    	Customer cust2 = (Customer)context2.getBean("CustomerBean");
    	System.out.println(cust2);
    	
    }

}
