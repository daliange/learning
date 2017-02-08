package org.spring.list;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App 
{
    public static void main( String[] args )
    {
    	@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("list/applicationContext.xml");

    	Customer cust = (Customer)context.getBean("CustomerBean");
    	System.out.println(cust);
    	
    }
}