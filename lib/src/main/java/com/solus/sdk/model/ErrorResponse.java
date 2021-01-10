package com.solus.sdk.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse{

    private String message;
    private Integer code;
    private String status;
   private  Map<String, String> details;

   public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public Integer getCode() {
	return code;
}
public void setCode(Integer code) {
	this.code = code;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public Map<String, String> getDetails() {
	return details;
}
public void setDetails(Map<String, String> details) {
	this.details = details;
}

    
}
