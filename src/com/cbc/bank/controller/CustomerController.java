package com.cbc.bank.controller;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.cbc.bank.impl.CustomerDaoImpl;
import com.cbc.bank.model.Customer;
import com.cbc.bank.util.SpringJDBCUtil;

public class CustomerController {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Logger log = Logger.getLogger("org.hibernate");
		log.setLevel(Level.OFF);
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
		
		Scanner in=new Scanner(System.in);
		
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringJDBCUtil.class);

		CustomerDaoImpl csdao = (CustomerDaoImpl) applicationContext.getBean("CustomerDaoImpl");

	
		// update password
		System.out.println("*****Updating customer password based on username*****");
		Customer customer=null;
		
		System.out.println("Enter the Username:");
		String user=in.next();
		customer=csdao.findByUsername(user);
		
		while(customer==null) {
			System.out.println("Enter the correct Username:");
			user=in.next();
			customer=csdao.findByUsername(user);
			if(customer!=null) {
				break;
			}
		}System.out.println("**"+customer.toString());
		System.out.println("Enter the new password:");
		String password=in.next();	
			
		customer.setCustomerPassword(password);
		//System.out.println(customer.getCustomerName());
		//System.out.println("*mmm*"+customer.getCustomerBalance());
		boolean result=csdao.updatePassword(customer);
		if(result) {
			System.out.println("Customer Password Updated Successfully.");
		}else {
			System.out.println("Something went wrong.Try again...");
		}
		
		
		//fund transfer
		System.out.println("*****Fund transfer between 2 customer*****");
		Customer receiver=null;
		int senderAccountNo=10002;
		Customer sender=csdao.findByAccount(senderAccountNo);
		
		
		System.out.println("Enter the account number:");
		int receiverAccountNo=in.nextInt();
		
		 receiver=csdao.findByAccount(receiverAccountNo);
			
		while( receiver==null) {
			System.out.println("Enter the correct account number:");
			receiverAccountNo=in.nextInt();
			 receiver=csdao.findByAccount(receiverAccountNo);
			if( receiver!=null) {
				break;
			}
		}	
		
		System.out.println("Enter the amount to be transfered:");
		float amount=in.nextFloat();
		boolean transfer=csdao.fundTransfer(sender, receiver, amount);
		
		if(transfer) {
			System.out.println("Fund was transfered successfully.");
		}else {
			System.out.println("You have insufficient balance to transfer fund.");
		}
	}

}
