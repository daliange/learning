package org.spring.jdbc.dao;

import org.spring.jdbc.model.Customer;

public interface CustomerDAO {
	
	public void insert(Customer customer);
	public Customer findByCustomerId(int custId);

}
