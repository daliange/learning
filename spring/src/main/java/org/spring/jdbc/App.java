package org.spring.jdbc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	
	public static void main( String[] args )
    {
    	ApplicationContext context = 
    		new ClassPathXmlApplicationContext("jdbc/applicationContext.xml");
    	Customer customer = new Customer(1, "yiibai",29);
    	
        CustomerDAO customerDAO = (CustomerDAO) context.getBean("customerDAO");
        customerDAO.insert(customer);
        CustomerDAO JdbcTemplateCustomDAO = (CustomerDAO) context.getBean("jdbcTemplateCustomDAO");
        JdbcTemplateCustomDAO.insert(customer);
        CustomerDAO jdbcDaoSupportCustomDAO = (CustomerDAO) context.getBean("jdbcDaoSupportCustomDAO");
        jdbcDaoSupportCustomDAO.insert(customer);
        
    	
        Customer customer1 = customerDAO.findByCustomerId(1);
        System.out.println(customer1);
        
    }

}
