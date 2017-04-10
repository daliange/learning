package org.spring.jdbc;

public interface CustomerDAO {
	
	public void insert(Customer customer);
	public void jdbcTemplateinsert(Customer customer);
	public void JdbcDaoSupportinsert(Customer customer);
	public Customer findByCustomerId(int custId);

}
