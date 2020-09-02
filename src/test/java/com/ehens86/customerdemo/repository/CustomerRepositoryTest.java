//package com.ehens86.customerdemo.repository;
//
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.ehens86.customerdemo.dao.Customer;
//
//@RunWith(MockitoJUnitRunner.class)
//@SpringBootTest
//public class CustomerRepositoryTest {
//
//	@Mock
//	CustomerRepository customerRepoMock;
//	
//	@Test
//	public void testAllCustomers() throws Exception {
//		CustomerRepository testSubject;
//		List<Customer> result;
//
//		// default test
//		testSubject = createTestSubject();
//		result = Whitebox.invokeMethod(testSubject, "allCustomers");
//	}
//
//	@Test
//	public void testCustomersWithUpcomingBirthdays() throws Exception {
//		CustomerRepository testSubject;
//		Integer dayCutoff = 0;
//		List<Customer> result;
//
//		// default test
//		testSubject = createTestSubject();
//		result = Whitebox.invokeMethod(testSubject, "customersWithUpcomingBirthdays", new Object[] { dayCutoff });
//	}
//}