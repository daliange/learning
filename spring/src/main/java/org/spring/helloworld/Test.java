package org.spring.helloworld;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"helloworld/applicationContext.xml"); 
		
		HelloWorld helloWorld = (HelloWorld) context.getBean("helloBean");
		helloWorld.printHello();
		
		HahaWorld hahaWorld = (HahaWorld) context.getBean("hahaBean");
		hahaWorld.printHello();
	}
}