package org.spring.jdbc;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component("jdbcTemplateCustomDAO")
public class JdbcTemplateCustomDAO implements CustomerDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void insert(Customer customer) {
		String sql = "INSERT INTO CUSTOMER " +
				"(CUST_ID, NAME, AGE) VALUES (?, ?, ?)";
					 
			jdbcTemplate.update(sql, new Object[] { customer.getCustId(),
				customer.getName(),customer.getAge()  
			});
		
	}


	@Override
	public Customer findByCustomerId(int custId) {
		// TODO Auto-generated method stub
		return null;
	}

}
