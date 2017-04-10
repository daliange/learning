package org.spring.jdbc;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

@Component("jdbcDaoSupportCustomDAO")
public class JdbcDaoSupportCustomDAO extends JdbcDaoSupport implements CustomerDAO{
	
	@Autowired
	private DataSource dataSource;
	
	private JdbcTemplate jdbcTemplate;

	@Override
	public void insert(Customer customer) {
		String sql = "INSERT INTO CUSTOMER " +
				"(CUST_ID, NAME, AGE) VALUES (?, ?, ?)";
					 
			getJdbcTemplate().update(sql, new Object[] { customer.getCustId(),
					customer.getName(),customer.getAge()  
			});
	}

	@Override
	public Customer findByCustomerId(int custId) {
		// TODO Auto-generated method stub
		return null;
	}

}
