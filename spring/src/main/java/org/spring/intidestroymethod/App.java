package org.spring.intidestroymethod;


import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class App 
{
    public static void main( String[] args )
    {
    	ConfigurableApplicationContext context = 
		new ClassPathXmlApplicationContext("intidestroymethod/applicationContext.xml");
	
    	CustomerService cust = (CustomerService)context.getBean("customerService");
    	System.out.println(cust);
    	CustomerServiceTwo cust2 = (CustomerServiceTwo)context.getBean("customerServiceTwo");
    	System.out.println(cust2);
    	
    	
    	context.close();
    }
}
