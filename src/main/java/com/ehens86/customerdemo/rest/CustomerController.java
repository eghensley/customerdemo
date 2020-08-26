package com.ehens86.customerdemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ehens86.customerdemo.service.CustomerService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("customers")
@Api(value = "Customer Information")
public class CustomerController {
		
	@Autowired
	CustomerService customerService;
	
	@GetMapping("/all")
	public List getCustomers() {
	    return customerService.getCustomerList();
	}
	
	@GetMapping("/birthdays/{cutoff}")
	public List getBirthdayCustomers(@PathVariable Integer cutoff) {
	    return customerService.getUpcomingBirthdayCustomerList(cutoff);
	}
}
