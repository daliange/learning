package org.spring.javaconfigimport;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {
	
	public static void main(String[] args) {

		@SuppressWarnings("resource")
		ApplicationContext context = new AnnotationConfigApplicationContext(
				AppConfig.class);

		CustomerBo customer = (CustomerBo) context.getBean("customer");
		customer.printMsg("Hello 11");

		SchedulerBo scheduler = (SchedulerBo) context.getBean("scheduler");
		scheduler.printMsg("Hello 22");

	}

}
