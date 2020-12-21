package com.solus.sdk.model;

public class Payment {
    
	
	private String apiKey;
    private String pan;
    private String expDate;
    private String ipin;
    private Float tranAmount;

	
	public Payment() {
		
	}

	public Payment(String apiKey, String pan, String expDate, String ipin, Float tranAmount) {
				this.apiKey = apiKey;
		this.pan = pan;
		this.expDate = expDate;
		this.ipin = ipin;
		this.tranAmount = tranAmount;
	}

	public Float getTranAmount() {
		return tranAmount;
	}
	public void setTranAmount(Float tranAmount) {
		this.tranAmount = tranAmount;
	}
	
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	public String getIpin() {
		return ipin;
	}
	public void setIPin(String ipin) {
		this.ipin = ipin;
	}

}
