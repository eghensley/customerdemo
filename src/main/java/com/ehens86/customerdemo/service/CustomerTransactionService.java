package com.ehens86.customerdemo.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ehens86.customerdemo.dao.Customer;
import com.ehens86.customerdemo.dao.CustomerTransactionView;
import com.ehens86.customerdemo.pojo.GetResponse;
import com.ehens86.customerdemo.repository.CustomerTransactionViewRepository;

@Service
public class CustomerTransactionService {
	private static final Logger LOG = Logger.getLogger(CustomerTransactionService.class.toString());

	private final CustomerTransactionViewRepository customerTransactionRepo;

	@Autowired
	public CustomerTransactionService(CustomerTransactionViewRepository customerTransactionRepo) {
		this.customerTransactionRepo = customerTransactionRepo;
	}

	public GetResponse getCustomerTransactionList() {
		String errorString = null;
		try {
			List<CustomerTransactionView> res = customerTransactionRepo.findAllStats();
			return new GetResponse(HttpStatus.OK, errorString, res);
		} catch (Exception e) {
			errorString = e.getLocalizedMessage();
			LOG.log(Level.SEVERE, errorString, e);
			return new GetResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorString, null);
		}
	}
}
