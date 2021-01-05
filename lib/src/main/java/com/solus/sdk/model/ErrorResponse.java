package com.solus.sdk.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {

	private String message;
	private String status;
    private String responseMessage;
    private Integer responseCode;

	public ErrorResponse() {
		
	}
	public ErrorResponse(String responseMessage, Integer responseCode) {
		
		this.responseMessage = responseMessage;
		this.responseCode = responseCode;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public Integer getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}

    
}
