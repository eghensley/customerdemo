package com.ehens86.customerdemo.pojo;

import java.util.Date;

import org.springframework.http.HttpStatus;

public class GetResponse extends GeneralApiResponse {
	private Object response;
	
	public GetResponse() {
		
	}

	public GetResponse(HttpStatus status, String errorMsg, Object response) {
		this.timestamp = new Date();
		this.status = status;
		this.errorMsg = errorMsg;
		this.response = response;
	}
	
	/**
	 * @return the response
	 */
	public Object getResponse() {
		return response;
	}

	/**
	 * @param response the response to set
	 */
	public void setResponse(Object response) {
		this.response = response;
	}
	
	
	
}
