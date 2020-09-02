package com.ehens86.customerdemo.dao;

import java.util.Objects;

import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

@Document
public class CustomerTransactionView {
	@Id
	private String customerTransactionId;
	
	@Field
	private Integer sum;
	
	@Field
	private Integer count;
	
	@Field
	private Integer min;
	
	@Field
	private Integer max;
	
	@Field
	private Integer sumsqr;

	public CustomerTransactionView(String customerTransactionId, Integer sum, Integer count, Integer min, Integer max,
			Integer sumsqr) {
		this.customerTransactionId = customerTransactionId;
		this.sum = sum;
		this.count = count;
		this.min = min;
		this.max = max;
		this.sumsqr = sumsqr;
	}
	
	public CustomerTransactionView() {
		
	}

	/**
	 * @return the customerTransactionId
	 */
	public String getCustomerTransactionId() {
		return customerTransactionId;
	}

	/**
	 * @param customerTransactionId the customerTransactionId to set
	 */
	public void setCustomerTransactionId(String customerTransactionId) {
		this.customerTransactionId = customerTransactionId;
	}

	/**
	 * @return the sum
	 */
	public Integer getSum() {
		return sum;
	}

	/**
	 * @param sum the sum to set
	 */
	public void setSum(Integer sum) {
		this.sum = sum;
	}

	/**
	 * @return the count
	 */
	public Integer getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * @return the min
	 */
	public Integer getMin() {
		return min;
	}

	/**
	 * @param min the min to set
	 */
	public void setMin(Integer min) {
		this.min = min;
	}

	/**
	 * @return the max
	 */
	public Integer getMax() {
		return max;
	}

	/**
	 * @param max the max to set
	 */
	public void setMax(Integer max) {
		this.max = max;
	}

	/**
	 * @return the sumsqr
	 */
	public Integer getSumsqr() {
		return sumsqr;
	}

	/**
	 * @param sumsqr the sumsqr to set
	 */
	public void setSumsqr(Integer sumsqr) {
		this.sumsqr = sumsqr;
	}

	@Override
	public int hashCode() {
		return Objects.hash(count, customerTransactionId, max, min, sum, sumsqr);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof CustomerTransactionView)) {
			return false;
		}
		CustomerTransactionView other = (CustomerTransactionView) obj;
		return Objects.equals(count, other.count) && Objects.equals(customerTransactionId, other.customerTransactionId)
				&& Objects.equals(max, other.max) && Objects.equals(min, other.min) && Objects.equals(sum, other.sum)
				&& Objects.equals(sumsqr, other.sumsqr);
	}

	@Override
	public String toString() {
		return "CustomerTransactionView [customerTransactionId=" + customerTransactionId + ", sum=" + sum + ", count="
				+ count + ", min=" + min + ", max=" + max + ", sumsqr=" + sumsqr + "]";
	}
	
	
}
