package com.ehens86.customerdemo.rest;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ehens86.customerdemo.pojo.GetResponse;
import com.ehens86.customerdemo.service.CustomerTransactionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("custTrans")
@Api(value = "Customer Transaction Information")
public class CustomerTransactionController {
	private static final Logger LOG = Logger.getLogger(CustomerTransactionController.class.toString());
	
	@Autowired
	CustomerTransactionService customerTransactionService;

	private static String CONTROLLER_ERROR = "%s [%s] Request Failed with %s";

	@ApiOperation(value = "Get all customer transaction summary stats")
	@GetMapping("/all/summary")
	public ResponseEntity<GetResponse> getCustomerTransactionSummaryStats() {
		try {
			GetResponse response = customerTransactionService.getCustomerTransactionList();
			return new ResponseEntity<>(response, response.getStatus());
		} catch (Exception e) {
			String errorMsg = String.format(CONTROLLER_ERROR, "getCustomerTransactionSummaryStats", "GET", e.getClass().getName());
			LOG.log(Level.SEVERE, errorMsg, e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
