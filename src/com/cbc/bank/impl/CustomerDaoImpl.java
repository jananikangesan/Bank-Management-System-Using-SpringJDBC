package com.cbc.bank.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cbc.bank.dao.CustomerDao;
import com.cbc.bank.model.Customer;

@Repository
public class CustomerDaoImpl implements CustomerDao{
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public Customer findByUsername(String username) {
		// TODO Auto-generated method stub
		
		try {
            return jdbcTemplate.queryForObject(CustomerDao.FIND_USERNAME_QUERY, new Object[]{username}, new BeanPropertyRowMapper<>(Customer.class));
        } catch (DataAccessException e) {
            return null;
        }
	}

	@Override
	public boolean updatePassword(Customer customer) {
		// TODO Auto-generated method stub
		
		try {
	        int result = jdbcTemplate.update(CustomerDao.UPDATE_PASSWORD_QUERY, new Object[] { customer.getCustomerPassword(), customer.getCustomerUsername()});
	        System.out.println(result);
	        return result > 0;
	    } catch (DataAccessException e) {
	       
	        e.printStackTrace(); 
	        return false; 
	    }
	}

	@Override
	public boolean fundTransfer(Customer sender, Customer receiver, float amount) {
		// TODO Auto-generated method stub
		boolean result=false;
		float senderBalance=sender.getCustomerBalance()-amount;
		boolean updateSenderResult=false;
		boolean updateReceiverResult=false;
		if(senderBalance>0) {
			sender.setCustomerBalance(senderBalance);
			updateSenderResult=updateBalance(sender);
			float receiverBalance=receiver.getCustomerBalance()+amount;
			receiver.setCustomerBalance(receiverBalance);
			updateReceiverResult=updateBalance(receiver);
		}
		if(updateReceiverResult && updateSenderResult) {
			result=true;
		}
		
		return result;
	}

	@Override
	public Customer findByAccount(int accountNo) {
		// TODO Auto-generated method stub
		try {
            return jdbcTemplate.queryForObject(CustomerDao.FIND_ACCOUNT_QUERY, new Object[]{accountNo}, new BeanPropertyRowMapper<>(Customer.class));
        } catch (DataAccessException e) {
            return null;
        }
	}

	@Override
	public boolean updateBalance(Customer customer) {
		// TODO Auto-generated method stub
		
		try {
	        int result = jdbcTemplate.update(CustomerDao.UPDATE_BALANCE_QUERY, new Object[] { customer.getCustomerBalance(), customer.getCustomerId()});
	        
	        return result > 0;
	    } catch (DataAccessException e) {
	       
	        e.printStackTrace(); 
	        return false; 
	    }
	}
	

}
