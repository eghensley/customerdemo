package com.ehens86.customerdemo.dao;

import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Generated;

import com.ehens86.customerdemo.enums.EntityTypeEnum;

@Generated(value = "org.junit-tools-1.1.0")
public class StateTest {

	private static final String TEST_COUNTRY = "country::usa";
	private static final String TEST_NAME = "New York";
	private static final String TEST_ABBREV = "New York";
	private static final EntityTypeEnum TEST_TYPE = EntityTypeEnum.state;
	
	
	
	private State createTestSubject() {
		return new State(TEST_COUNTRY, TEST_TYPE, TEST_NAME, TEST_ABBREV);
	}

	@Test
	public void testGetType() throws Exception {
		State testSubject;

		// default test
		testSubject = createTestSubject();

		Assert.assertEquals(TEST_TYPE, testSubject.getType());
	}

	@Test
	public void testSetType() throws Exception {
		State testSubject;
		EntityTypeEnum type = EntityTypeEnum.country;

		// default test
		testSubject = createTestSubject();
		testSubject.setType(type);

		Assert.assertEquals(EntityTypeEnum.country, testSubject.getType());
	}

	@Test
	public void testGetName() throws Exception {
		State testSubject;

		// default test
		testSubject = createTestSubject();

		Assert.assertEquals(TEST_NAME, testSubject.getName());

	}

	@Test
	public void testSetName() throws Exception {
		State testSubject;
		String name = "Florida";

		// default test
		testSubject = createTestSubject();
		testSubject.setName(name);

		Assert.assertEquals(name, testSubject.getName());

	}

	@Test
	public void testGetAbbrev() throws Exception {
		State testSubject;

		// default test
		testSubject = createTestSubject();
		Assert.assertEquals(TEST_ABBREV, testSubject.getAbbrev());
	}

	@Test
	public void testSetAbbrev() throws Exception {
		State testSubject;
		String abbrev = "FL";

		// default test
		testSubject = createTestSubject();
		testSubject.setAbbrev(abbrev);

		Assert.assertEquals(abbrev, testSubject.getAbbrev());

	}

	@Test
	public void testGetCountry() throws Exception {
		State testSubject;

		// default test
		testSubject = createTestSubject();
		Assert.assertEquals(TEST_COUNTRY, testSubject.getCountry());
	}

	@Test
	public void testSetCountry() throws Exception {
		State testSubject;
		String country = "country::mexico";

		// default test
		testSubject = createTestSubject();
		testSubject.setCountry(country);
		
		Assert.assertEquals(country, testSubject.getCountry());
	}
}