package com.ehens86.customerdemo.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.Assert;

import com.ehens86.customerdemo.dao.Customer;
import com.ehens86.customerdemo.kafka.KafkaProducer;
import com.ehens86.customerdemo.pojo.GeneralApiResponse;
import com.ehens86.customerdemo.pojo.GetResponse;
import com.ehens86.customerdemo.repository.CustomerRepository;
import com.google.gson.Gson;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CustomerServiceTest {

	@Mock
	CustomerRepository customerRepoMock;

	@Mock
	KafkaProducer kafkaProdMock;

	@InjectMocks
	CustomerService customerService;

	@Test
	public void testGetCustomerList() throws Exception {
		List<Customer> customers = new ArrayList<>();
		Mockito.when(customerRepoMock.allCustomers()).thenReturn(customers);
		GetResponse response = customerService.getCustomerList();
		Assert.assertEquals(HttpStatus.OK, response.getStatus());
		Assert.assertNull(response.getErrorMsg());
	}

	@Test
	public void testGetCustomerListError() throws Exception {
		Mockito.when(customerRepoMock.allCustomers()).thenThrow(new IllegalArgumentException("Test Error"));
		GetResponse response = customerService.getCustomerList();
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
		Assert.assertNotNull(response.getErrorMsg());
	}

	@Test
	public void testGetUpcomingBirthdayCustomerList() throws Exception {
		List<Customer> customers = new ArrayList<>();
		Mockito.when(customerRepoMock.customersWithUpcomingBirthdays(5)).thenReturn(customers);
		GetResponse response = customerService.getUpcomingBirthdayCustomerList(5);
		Assert.assertEquals(HttpStatus.OK, response.getStatus());
		Assert.assertNull(response.getErrorMsg());
	}

	@Test
	public void testGetUpcomingBirthdayCustomerListError() throws Exception {
		Mockito.when(customerRepoMock.customersWithUpcomingBirthdays(5))
				.thenThrow(new IllegalArgumentException("Test Error"));
		GetResponse response = customerService.getUpcomingBirthdayCustomerList(5);
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
		Assert.assertNotNull(response.getErrorMsg());
	}

	@Test
	public void testPublishCustomerToKafka() throws Exception {
		Customer customer = new Customer();
		GeneralApiResponse response = customerService.publishCustomerToKafka(customer);
		Assert.assertEquals(HttpStatus.OK, response.getStatus());
		Assert.assertNull(response.getErrorMsg());
	}

//	@Test
//	public void testPublishCustomerToKafkaError() throws Exception {
//		Gson gson = new Gson();
//		Customer customer = new Customer();
//		String message = gson.toJson(customer);
//		GeneralApiResponse response = customerService.publishCustomerToKafka(customer);
//		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
//		Assert.assertNotNull(response.getErrorMsg());
//	}

}