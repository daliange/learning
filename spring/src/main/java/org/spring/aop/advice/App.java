package org.spring.aop.advice;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	
	public static void main(String[] args) {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("aop/advice/Spring-Customer.xml");

		CustomerService cust = (CustomerService) appContext.getBean("customerService");
		CustomerService cust2 = (CustomerService) appContext.getBean("customerServiceProxy");

		System.out.println("************1*************");
		cust.printName();
		System.out.println("************2*************");
		cust.printURL();
		System.out.println("************3*************");
		
		
		System.out.println("************4*************");
		cust2.printName();
		System.out.println("************5*************");
		cust2.printURL();
		System.out.println("************6*************");
		try {
			cust2.printThrowException();
		} catch (Exception e) {

		}

	}

}
