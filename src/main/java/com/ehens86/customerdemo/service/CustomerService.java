package com.ehens86.customerdemo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ehens86.customerdemo.repository.CustomerRepository;

@Service
public class CustomerService {

	private final CustomerRepository customerRepo;
	
	public CustomerService(CustomerRepository customerRepo) {
		this.customerRepo = customerRepo;
	}
	
	public List getCustomerList() {
		return customerRepo.allCustomers();
	}
	
	public List getUpcomingBirthdayCustomerList(Integer cutoff) {
		return customerRepo.customersWithUpcomingBirthdays(cutoff);
	}
}
