package com.ehens86.customerdemo.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ehens86.customerdemo.dao.Customer;
import com.ehens86.customerdemo.kafka.KafkaProducer;
import com.ehens86.customerdemo.pojo.GeneralApiResponse;
import com.ehens86.customerdemo.pojo.GetResponse;
import com.ehens86.customerdemo.repository.CustomerRepository;
import com.google.gson.Gson;

@Service
public class CustomerService {
	private static final Logger LOG = Logger.getLogger(CustomerService.class.toString());

	private final CustomerRepository customerRepo;
	private final KafkaProducer kafkaProducer;
	
	public CustomerService(CustomerRepository customerRepo, KafkaProducer kafkaProducer) {
		this.customerRepo = customerRepo;
		this.kafkaProducer = kafkaProducer;
	}

	public GetResponse getCustomerList() {
		String errorString = null;
		try {
			List<Customer> res = customerRepo.allCustomers();
			return new GetResponse(HttpStatus.OK, errorString, res);
		} catch (Exception e) {
			errorString = e.getLocalizedMessage();
			LOG.log(Level.SEVERE, errorString, e);
			return new GetResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorString, null);
		}
	}

	public GetResponse getUpcomingBirthdayCustomerList(Integer cutoff) {
		String errorString = null;

		try {
			List<Customer> res = customerRepo.customersWithUpcomingBirthdays(cutoff);
			return new GetResponse(HttpStatus.OK, errorString, res);
		} catch (Exception e) {
			errorString = e.getLocalizedMessage();
			LOG.log(Level.SEVERE, errorString, e);
			return new GetResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorString, null);
		}
	}
	
	public GeneralApiResponse publishCustomerToKafka(Customer payload) {
		String errorString = null;

		try {
			Gson gson = new Gson();
			kafkaProducer.sendMessage(gson.toJson(payload));
			return new GeneralApiResponse(HttpStatus.OK, errorString);
		} catch (Exception e) {
			errorString = e.getLocalizedMessage();
			LOG.log(Level.SEVERE, errorString, e);
			return new GeneralApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorString);
		}
	}
}
