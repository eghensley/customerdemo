package com.ehens86.customerdemo.dao;

import java.sql.Date;
import java.util.Objects;

import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import com.ehens86.customerdemo.enums.EntityTypeEnum;
import com.ehens86.customerdemo.enums.customer.GenderEnum;
import com.ehens86.customerdemo.enums.customer.StatusEnum;

@Document
public class Customer {

	@Id
	private String documentId;

	@Field
	private String firstName;

	@Field
	private String lastName;

	@Field("joinDate")
	private Date date;

	@Field
	private StatusEnum status;

	@Field
	private EntityTypeEnum type;

	@Field
	private Date dob;

	@Field
	private GenderEnum gender;

	@Field
	private String city;

	public Customer(String firstName, String lastName, Date date, StatusEnum status, EntityTypeEnum type, Date dob,
			GenderEnum gender, String city) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.date = date;
		this.status = status;
		this.type = type;
		this.dob = dob;
		this.gender = gender;
		this.city = city;
	}

//	public Customer(String firstName, String lastName, Date date, String status, String type, Date dob,
//			String gender, String city) {
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.date = date;
//		this.status = StatusEnum.valueOf(status.toUpperCase());
//		this.type = EntityTypeEnum.valueOf(type.toUpperCase());
//		this.dob = dob;
//		this.gender = GenderEnum.valueOf(gender.toUpperCase());
//		this.city = city;
//	}
	
	public Customer() {

	}

	/**
	 * @return the documentId
	 */
	public String getDocumentId() {
		return documentId;
	}

	/**
	 * @param documentId the documentId to set
	 */
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the status
	 */
	public StatusEnum getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	/**
	 * @return the type
	 */
	public EntityTypeEnum getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(EntityTypeEnum type) {
		this.type = type;
	}

	/**
	 * @return the dob
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * @param dob the dob to set
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}

	/**
	 * @return the gender
	 */
	public GenderEnum getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(GenderEnum gender) {
		this.gender = gender;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "Customer [documentId=" + documentId + ", firstName=" + firstName + ", lastName=" + lastName + ", date="
				+ date + ", status=" + status + ", type=" + type + ", dob=" + dob + ", gender=" + gender + ", city="
				+ city + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(city, date, dob, documentId, firstName, gender, lastName, status, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Customer)) {
			return false;
		}
		Customer other = (Customer) obj;
		return Objects.equals(city, other.city) && Objects.equals(date, other.date) && Objects.equals(dob, other.dob)
				&& Objects.equals(documentId, other.documentId) && Objects.equals(firstName, other.firstName)
				&& gender == other.gender && Objects.equals(lastName, other.lastName) && status == other.status
				&& type == other.type;
	}

}