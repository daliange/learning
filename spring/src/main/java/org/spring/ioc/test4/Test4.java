package org.spring.ioc.test4;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test4 {
	
    public static void main( String[] args )
    {
    	@SuppressWarnings("resource")
		ApplicationContext context = 
    	   new ClassPathXmlApplicationContext("ioc/test4/Spring-Common.xml");

    	OutputHelper output = (OutputHelper)context.getBean("OutputHelper");
    	output.generateOutput();
    	  
    }

}
