package com.ehens86.customerdemo.dao;

import java.util.Objects;

import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Field;
import com.ehens86.customerdemo.enums.EntityTypeEnum;

@Document
public class Transaction {

    @Field
    private EntityTypeEnum type;

    @Field
    private Integer amount;
    
    @Field
    private String customer;
    
    public Transaction(EntityTypeEnum type, Integer amount, String customer) {
    	this.type = type;
    	this.amount = amount;
    	this.customer = customer;
    }
    
    public Transaction() {
    	
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
	 * @return the amount
	 */
	public Integer getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	/**
	 * @return the customer
	 */
	public String getCustomer() {
		return customer;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(String customer) {
		this.customer = customer;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, customer, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Transaction)) {
			return false;
		}
		Transaction other = (Transaction) obj;
		return Objects.equals(amount, other.amount) && Objects.equals(customer, other.customer) && type == other.type;
	}

	@Override
	public String toString() {
		return "Transaction [type=" + type + ", amount=" + amount + ", customer=" + customer + "]";
	}
    
}
