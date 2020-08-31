package com.ehens86.customerdemo.kafka;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ehens86.customerdemo.dao.Customer;
import com.ehens86.customerdemo.repository.CustomerRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class KafkaConsumer {

	private final CustomerRepository customerRepo;

	private static final Logger LOG = Logger.getLogger(KafkaConsumer.class.toString());

	public KafkaConsumer(CustomerRepository customerRepo) {
		this.customerRepo = customerRepo;
	}

	@KafkaListener(topics = "test", groupId = "group_id")
	public void consume(String message) throws IOException {
		LOG.info(String.format("#### -> Consumed message -> %s", message));
		try {
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			Customer cust = gson.fromJson(message, Customer.class);
			customerRepo.save(cust);
			LOG.log(Level.INFO, String.format("Customer %s added to DB successfully", cust.getDocumentId()));
		} catch (Exception e) {
			String errorString = e.getLocalizedMessage();
			LOG.log(Level.SEVERE, errorString, e);
		}

	}
}
