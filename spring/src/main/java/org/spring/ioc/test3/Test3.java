package org.spring.ioc.test3;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * setter方法注入
 * 
 * **/
public class Test3 
{
    public static void main( String[] args )
    {
    	@SuppressWarnings("resource")
		ApplicationContext context = 
    	   new ClassPathXmlApplicationContext("ioc/test3/Spring-Common.xml");

    	OutputHelper output = (OutputHelper)context.getBean("OutputHelper");
    	output.generateOutput();
    	  
    }
}