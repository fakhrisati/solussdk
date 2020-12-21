package com.solus.sdk.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;


public class Request implements Serializable {

    private final String tranDateTime = getDate();
    private final String applicationId = "ACTSCon";
    private String pan;
    private String expDate;
    private String IPIN;
    private Float tranAmount;
    private String tranCurrencyCode = "SDG";
	private String UUID;

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String UUID) {
		this.UUID = UUID;
	}



    public String getDate(){
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyHHmmss", Locale.US);
        Date date = new Date();
        return dateFormat.format(date);
    }

	public String getTranDateTime() {
		return tranDateTime;
	}
	public String getApplicationId() {
		return applicationId;
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
	public String getIPIN() {
		return IPIN;
	}
	public void setIPIN(String iPIN) {
		IPIN = iPIN;
	}
	public Float getTranAmount() {
		return tranAmount;
	}
	public void setTranAmount(Float tranAmount) {
		this.tranAmount = tranAmount;
	}

	public String getTranCurrencyCode() {
		return tranCurrencyCode;
	}

}