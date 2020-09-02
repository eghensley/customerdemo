package com.ehens86.customerdemo.kafka;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.stereotype.Service;

import com.ehens86.customerdemo.dao.Customer;
import com.ehens86.customerdemo.pojo.GeneralApiResponse;
import com.ehens86.customerdemo.repository.CustomerRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class KafkaConsumer {

	private final CustomerRepository customerRepo;
	private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

	private static final Logger LOG = Logger.getLogger(KafkaConsumer.class.toString());

	@Autowired
	public KafkaConsumer(CustomerRepository customerRepo, KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry) {
		this.customerRepo = customerRepo;
		this.kafkaListenerEndpointRegistry = kafkaListenerEndpointRegistry;
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory kafkaListenerContainerFactory(
			ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
			ConsumerFactory<Object, Object> kafkaConsumerFactory, KafkaTemplate<Object, Object> template) {
		ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
		configurer.configure(factory, kafkaConsumerFactory);
		factory.setErrorHandler(new SeekToCurrentErrorHandler(new DeadLetterPublishingRecoverer(template), 3));
		return factory;
	}

	public GeneralApiResponse pauseListener() {
		String errorString = null;
		try {
			MessageListenerContainer listenerContainer = kafkaListenerEndpointRegistry.getListenerContainer("group_id");
			listenerContainer.pause();
			return new GeneralApiResponse(HttpStatus.OK, errorString);
		} catch (Exception e) {
			errorString = e.getLocalizedMessage();
			LOG.log(Level.SEVERE, errorString, e);
			return new GeneralApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorString);
		}
	}

	public GeneralApiResponse resumeListener() {
		String errorString = null;

		try {
			MessageListenerContainer listenerContainer = kafkaListenerEndpointRegistry.getListenerContainer("group_id");
			listenerContainer.resume();
			return new GeneralApiResponse(HttpStatus.OK, errorString);
		} catch (Exception e) {
			errorString = e.getLocalizedMessage();
			LOG.log(Level.SEVERE, errorString, e);
			return new GeneralApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorString);
		}
	}

	@KafkaListener(topics = "test", groupId = "group_id")
	public void consume(String message) throws Exception {
		LOG.info(String.format("#### -> Consumed message -> %s", message));
		try {
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			Customer cust = gson.fromJson(message, Customer.class);
			customerRepo.save(cust);
			LOG.log(Level.INFO, String.format("Customer %s added to DB successfully", cust.getDocumentId()));
		} catch (Exception e) {
			String errorString = e.getLocalizedMessage();
			LOG.log(Level.SEVERE, errorString, e);
			throw new Exception(e);
		}

	}

}
