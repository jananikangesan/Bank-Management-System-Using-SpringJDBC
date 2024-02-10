package com.cbc.bank.util;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.cbc.bank.impl.CustomerDaoImpl;


@Configuration
@ComponentScan(basePackages="com.cbc.bank")
public class SpringJDBCUtil {

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:xe");//change url
		dataSource.setUsername("system");
		dataSource.setPassword("cbc123");


		return dataSource;
	}

	@Bean
	public JdbcTemplate jdbcTemplate() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource());
		return jdbcTemplate;
	}

	@Bean
	  public CustomerDaoImpl customerDaoImpl(){
		CustomerDaoImpl custDao = new CustomerDaoImpl();
	    custDao.setJdbcTemplate(jdbcTemplate());
	    return custDao;
	  }
}
