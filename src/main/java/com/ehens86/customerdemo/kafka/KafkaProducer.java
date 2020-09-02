package com.ehens86.customerdemo.kafka;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public class KafkaProducer {

	private static final Logger LOG = Logger.getLogger(KafkaProducer.class.toString());

	@Value("${app.topic}")
	private String topic;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void sendMessage(String message) {
		LOG.info(String.format("#### -> Producing message -> %s", message));
		ListenableFuture<SendResult<String, String>> kafkaResp = this.kafkaTemplate.send(topic, message);
	}

}