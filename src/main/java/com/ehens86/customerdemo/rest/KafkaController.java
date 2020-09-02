package com.ehens86.customerdemo.rest;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ehens86.customerdemo.kafka.KafkaConsumer;
import com.ehens86.customerdemo.pojo.GeneralApiResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("kafka/admin")
@Api(value = "Kafka Admin")
public class KafkaController {

	private static final Logger LOG = Logger.getLogger(KafkaController.class.toString());

	@Autowired
	KafkaConsumer kafkaConsumer;

	private static String CONTROLLER_ERROR = "%s [%s] Request Failed with %s";

	@ApiOperation(value = "Pause Kafka Consumer")
	@GetMapping("/pause")
	public ResponseEntity<GeneralApiResponse> pauseKafka() {
		try {
			GeneralApiResponse response = kafkaConsumer.pauseListener();
			return new ResponseEntity<>(response, response.getStatus());
		} catch (Exception e) {
			String errorMsg = String.format(CONTROLLER_ERROR, "getCustomers", "GET", e.getClass().getName());
			LOG.log(Level.SEVERE, errorMsg, e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "Resume Kafka Consumer")
	@GetMapping("/resume")
	public ResponseEntity<GeneralApiResponse> resumeKafka() {
		try {
			GeneralApiResponse response = kafkaConsumer.resumeListener();
			return new ResponseEntity<>(response, response.getStatus());
		} catch (Exception e) {
			String errorMsg = String.format(CONTROLLER_ERROR, "getCustomers", "GET", e.getClass().getName());
			LOG.log(Level.SEVERE, errorMsg, e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
