package com.ehens86.customerdemo.pojo;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import javax.annotation.Generated;

import org.junit.Assert;
import org.junit.Test;

@Generated(value = "org.junit-tools-1.1.0")
public class GetResponseTest {

	private GetResponse createTestSubject() {
		return new GetResponse(HttpStatus.OK, "test error", "test object");
	}

	@Test
	public void testGetResponse() throws Exception {
		GetResponse testSubject;
		Object result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.getResponse();
		Assert.assertEquals(result, "test object");
	}

	@Test
	public void testSetResponse() throws Exception {
		GetResponse testSubject;
		Object response = "new test object";

		// default test
		testSubject = createTestSubject();
		testSubject.setResponse(response);
		Assert.assertEquals(testSubject.getResponse(), "new test object");

	}
}