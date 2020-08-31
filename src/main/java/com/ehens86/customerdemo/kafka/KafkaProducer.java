package com.ehens86.customerdemo.kafka;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

	private static final Logger LOG = Logger.getLogger(KafkaProducer.class.toString());

	@Value("${app.topic}")
	private String topic;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void sendMessage(String message) {
		LOG.info(String.format("#### -> Producing message -> %s", message));
		this.kafkaTemplate.send(topic, message);
	}

}