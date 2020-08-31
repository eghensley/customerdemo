package com.ehens86.customerdemo.rest;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ehens86.customerdemo.dao.Customer;
import com.ehens86.customerdemo.pojo.GeneralApiResponse;
import com.ehens86.customerdemo.pojo.GetResponse;
import com.ehens86.customerdemo.service.CustomerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("customers")
@Api(value = "Customer Information")
public class CustomerController {
	private static final Logger LOG = Logger.getLogger(CustomerController.class.toString());

	@Autowired
	CustomerService customerService;

	private static String CONTROLLER_ERROR = "%s [%s] Request Failed with %s";

	@ApiOperation(value = "Get all customers")
	@GetMapping("/all")
	public ResponseEntity<GetResponse> getCustomers() {
		try {
			GetResponse response = customerService.getCustomerList();
			return new ResponseEntity<>(response, response.getStatus());
		} catch (Exception e) {
			String errorMsg = String.format(CONTROLLER_ERROR, "getCustomers", "GET", e.getClass().getName());
			LOG.log(Level.SEVERE, errorMsg, e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Get customers with upcoming birthdays")
	@GetMapping("/birthdays/{cutoff}")
	public ResponseEntity<GetResponse> getBirthdayCustomers(@PathVariable Integer cutoff) {
		try {
			GetResponse response = customerService.getUpcomingBirthdayCustomerList(cutoff);
			return new ResponseEntity<>(response, response.getStatus());
		} catch (Exception e) {
			String errorMsg = String.format(CONTROLLER_ERROR, "getUpcomingBirthdays", "GET", e.getClass().getName());
			LOG.log(Level.SEVERE, errorMsg, e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "Publish new customer to kafka")
	@PostMapping("/add/kafka")
	public ResponseEntity<GeneralApiResponse> addCustomerViaKafka(@RequestBody Customer reqPayload) {
		try {
			GeneralApiResponse response = customerService.publishCustomerToKafka(reqPayload);
			return new ResponseEntity<>(response, response.getStatus());
		} catch (Exception e) {
			String errorMsg = String.format(CONTROLLER_ERROR, "addCustomerViaKafka", "POST", e.getClass().getName());
			LOG.log(Level.SEVERE, errorMsg, e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
