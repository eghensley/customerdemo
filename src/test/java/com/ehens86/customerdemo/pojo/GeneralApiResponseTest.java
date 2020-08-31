package com.ehens86.customerdemo.pojo;

import java.util.Date;
import java.util.logging.Logger;

import javax.annotation.Generated;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;

@Generated(value = "org.junit-tools-1.1.0")
public class GeneralApiResponseTest {

	@Generated(value = "org.junit-tools-1.1.0")
	private Logger logger = Logger.getLogger(GeneralApiResponseTest.class.toString());

	private GeneralApiResponse createTestSubject() {
		return new GeneralApiResponse();
	}

	@Test
	public void testGetStatus() throws Exception {
		GeneralApiResponse testSubject;
		HttpStatus status = HttpStatus.ACCEPTED;

		// default test
		testSubject = createTestSubject();
		testSubject.setStatus(status);

		Assert.assertEquals(status, testSubject.status);
	}

	@Test
	public void testSetStatus() throws Exception {
		GeneralApiResponse testSubject;
		HttpStatus status = HttpStatus.ACCEPTED;

		// default test
		testSubject = createTestSubject();
		testSubject.setStatus(status);

		Assert.assertEquals(status, testSubject.status);
	}

	@Test
	public void testGetTimestamp() throws Exception {
		GeneralApiResponse testSubject;
		Date timestamp = new Date();

		// default test
		testSubject = createTestSubject();
		testSubject.setTimestamp(timestamp);

		Assert.assertEquals(timestamp, testSubject.getTimestamp());
	}

	@Test
	public void testSetTimestamp() throws Exception {
		GeneralApiResponse testSubject;
		Date timestamp = new Date();

		// default test
		testSubject = createTestSubject();
		testSubject.setTimestamp(timestamp);

		Assert.assertEquals(timestamp, testSubject.getTimestamp());
	}

	@Test
	public void testGetErrorMsg() throws Exception {
		GeneralApiResponse testSubject;
		String errorMsg = "test error";

		// default test
		testSubject = createTestSubject();
		testSubject.setErrorMsg(errorMsg);

		Assert.assertEquals(errorMsg, testSubject.getErrorMsg());
	}

	@Test
	public void testSetErrorMsg() throws Exception {
		GeneralApiResponse testSubject;
		String errorMsg = "test error";

		// default test
		testSubject = createTestSubject();
		testSubject.setErrorMsg(errorMsg);

		Assert.assertEquals(errorMsg, testSubject.getErrorMsg());
	}
}