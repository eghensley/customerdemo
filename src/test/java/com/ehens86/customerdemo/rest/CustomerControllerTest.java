package com.ehens86.customerdemo.rest;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.ehens86.customerdemo.dao.Customer;
import com.ehens86.customerdemo.pojo.GetResponse;
import com.ehens86.customerdemo.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CustomerController.class)
public class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerService customerServiceMock;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void testGetCustomers200() throws Exception {
		GetResponse successfulResponse = new GetResponse(HttpStatus.OK, null, new ArrayList<>());
		Mockito.when(customerServiceMock.getCustomerList()).thenReturn(successfulResponse);
		mockMvc.perform(MockMvcRequestBuilders.get("/customers/all")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testGetCustomers500() throws Exception {
		GetResponse failResponse = new GetResponse(HttpStatus.INTERNAL_SERVER_ERROR, null, new ArrayList<>());
		Mockito.when(customerServiceMock.getCustomerList()).thenReturn(failResponse);
		mockMvc.perform(MockMvcRequestBuilders.get("/customers/all"))
				.andExpect(MockMvcResultMatchers.status().isInternalServerError());
	}

	@Test
	public void testGetBirthdayCustomers200() throws Exception {
		GetResponse successfulResponse = new GetResponse(HttpStatus.OK, null, new ArrayList<>());
		Mockito.when(customerServiceMock.getUpcomingBirthdayCustomerList(5)).thenReturn(successfulResponse);
		mockMvc.perform(MockMvcRequestBuilders.get("/customers/birthdays/5"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testGetBirthdayCustomers500() throws Exception {
		GetResponse failResponse = new GetResponse(HttpStatus.INTERNAL_SERVER_ERROR, null, new ArrayList<>());
		Mockito.when(customerServiceMock.getUpcomingBirthdayCustomerList(15)).thenReturn(failResponse);
		mockMvc.perform(MockMvcRequestBuilders.get("/customers/birthdays/15"))
				.andExpect(MockMvcResultMatchers.status().isInternalServerError());
	}

	@Test
	public void testGetBirthdayCustomers400() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/customers/birthdays/x"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void testAddCustomerViaKafka200() throws Exception {
		GetResponse successResponse = new GetResponse(HttpStatus.OK, null, new ArrayList<>());
		Customer goodCustomer = new Customer();
		Mockito.when(customerServiceMock.publishCustomerToKafka(goodCustomer)).thenReturn(successResponse);
		mockMvc.perform(MockMvcRequestBuilders.post("/customers/add/kafka").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(goodCustomer)))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testAddCustomerViaKafka500() throws Exception {
		GetResponse failResponse = new GetResponse(HttpStatus.INTERNAL_SERVER_ERROR, null, new ArrayList<>());
		Customer badCustomer = new Customer();
		Mockito.when(customerServiceMock.publishCustomerToKafka(badCustomer)).thenReturn(failResponse);
		mockMvc.perform(MockMvcRequestBuilders.post("/customers/add/kafka").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(badCustomer)))
				.andExpect(MockMvcResultMatchers.status().isInternalServerError());
	}

	@Test
	public void testAddCustomerViaKafka400() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/customers/add/kafka"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
}