package org.spring.jdbc;

import org.spring.jdbc.dao.CustomerDAO;
import org.spring.jdbc.model.Customer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	
	 public static void main( String[] args )
	    {
	    	ApplicationContext context = 
	    		new ClassPathXmlApplicationContext("jdbc/applicationContext.xml");
	    	 
	        CustomerDAO customerDAO = (CustomerDAO) context.getBean("customerDAO");
	        Customer customer = new Customer(1, "yiibai",29);
	        customerDAO.insert(customer);
	    	
	        Customer customer1 = customerDAO.findByCustomerId(1);
	        System.out.println(customer1);
	        
	    }

}
