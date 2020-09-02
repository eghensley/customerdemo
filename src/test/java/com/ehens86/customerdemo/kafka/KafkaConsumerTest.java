package com.ehens86.customerdemo.kafka;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.ehens86.customerdemo.dao.Customer;
import com.ehens86.customerdemo.pojo.GetResponse;
import com.ehens86.customerdemo.repository.CustomerRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class KafkaConsumerTest {

	@Mock
	CustomerRepository customerRepoMock;
	
	@InjectMocks
	KafkaConsumer kafkaConsumerMock;
	
	@Test
	public void testConsume() throws Exception {
		Mockito.when(customerRepoMock.save(new Customer())).thenReturn(new Customer());
		kafkaConsumerMock.consume("{}");
	}
	
	@Test
	public void testConsumeBadDate() throws Exception {
		kafkaConsumerMock.consume("{\"date\":\"Nov 22, 1993\"}");
	}
}