//package com.ehens86.customerdemo.rest;
//
//import java.util.ArrayList;
//
//import org.junit.Test;
//import org.springframework.http.ResponseEntity;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.skyscreamer.jsonassert.JSONAssert;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.RequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import com.ehens86.customerdemo.pojo.GetResponse;
//import com.ehens86.customerdemo.service.CustomerService;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(value = CustomerController.class)
//public class CustomerControllerTest {
//
//	@Autowired
//	private MockMvc mockMvc;
//	
//	@MockBean
//	private CustomerService customerService;
//
//	@Test
//	public void testGetCustomers() throws Exception {
//		
//		Mockito.when(
//				customerService.getCustomerList().thenReturn(new GetResponse(HttpStatus.OK, null, new ArrayList<>()));
//		
//		CustomerController testSubject;
//		ResponseEntity<GetResponse> result;
//
//		// default test
//		testSubject = createTestSubject();
//		result = testSubject.getCustomers();
//	}
//
//	@Test
//	public void testGetBirthdayCustomers() throws Exception {
//		CustomerController testSubject;
//		Integer cutoff = 0;
//		ResponseEntity<GetResponse> result;
//
//		// default test
//		testSubject = createTestSubject();
//		result = testSubject.getBirthdayCustomers(cutoff);
//	}
//}