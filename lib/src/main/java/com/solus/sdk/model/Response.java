package com.solus.sdk.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Response implements Serializable{

    private String responseMessage;
    private String responseStatus;
    private Integer responseCode;
    private String tranDateTime;
    private String terminalId;
    private Integer systemTraceAuditNumber;
    private String clientId;
    private String PAN;
    private Float tranAmount;
    private Float tranFee;
    private Float additionalAmount;
    private Float acqTranFee;
    private Float issuerTranFee;
    private String pubKeyValue;
    private String tranCurrency;
    private String paymentInfo;
    private String fromAccount;
    private HashMap<String, Double> balance;
    private HashMap<String, String> billInfo;


 
    public String getResponseMessage() {
		return responseMessage;
	}


	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}


	public String getResponseStatus() {
		return responseStatus;
	}


	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}


	public Integer getResponseCode() {
		return responseCode;
	}


	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}


	public String getTerminalId() {
		return terminalId;
	}


	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}


	public Integer getSystemTraceAuditNumber() {
		return systemTraceAuditNumber;
	}


	public void setSystemTraceAuditNumber(Integer systemTraceAuditNumber) {
		this.systemTraceAuditNumber = systemTraceAuditNumber;
	}


	public String getPAN() {
		return PAN;
	}


	public void setPAN(String pAN) {
		PAN = pAN;
	}


	public Float getTranAmount() {
		return tranAmount;
	}


	public void setTranAmount(Float tranAmount) {
		this.tranAmount = tranAmount;
	}


	public Float getTranFee() {
		return tranFee;
	}


	public void setTranFee(Float tranFee) {
		this.tranFee = tranFee;
	}


	public Float getAdditionalAmount() {
		return additionalAmount;
	}


	public void setAdditionalAmount(Float additionalAmount) {
		this.additionalAmount = additionalAmount;
	}


	public Float getAcqTranFee() {
		return acqTranFee;
	}


	public void setAcqTranFee(Float acqTranFee) {
		this.acqTranFee = acqTranFee;
	}


	public Float getIssuerTranFee() {
		return issuerTranFee;
	}


	public void setIssuerTranFee(Float issuerTranFee) {
		this.issuerTranFee = issuerTranFee;
	}


	public String getPubKeyValue() {
		return pubKeyValue;
	}


	public void setPubKeyValue(String pubKeyValue) {
		this.pubKeyValue = pubKeyValue;
	}


	public String getTranCurrency() {
		return tranCurrency;
	}


	public void setTranCurrency(String tranCurrency) {
		this.tranCurrency = tranCurrency;
	}


	public String getPaymentInfo() {
		return paymentInfo;
	}


	public void setPaymentInfo(String paymentInfo) {
		this.paymentInfo = paymentInfo;
	}


	public String getFromAccount() {
		return fromAccount;
	}


	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}


	public HashMap<String, Double> getBalance() {
		return balance;
	}


	public void setBalance(HashMap<String, Double> balance) {
		this.balance = balance;
	}


	public HashMap<String, String> getBillInfo() {
		return billInfo;
	}


	public void setBillInfo(HashMap<String, String> billInfo) {
		this.billInfo = billInfo;
	}


	public void setTranDateTime(String tranDateTime) {
		this.tranDateTime = tranDateTime;
	}


	public void setClientId(String clientId) {
		this.clientId = clientId;
	}


	public String getAvailableBalance() {
        NumberFormat formatter = new DecimalFormat("#0.00");
        return formatter.format(this.getBalance().get("available"));

    }

   
    public String getTranDateTime() {
        Date newDate = new Date();
        // we have to set the locale as this will fail in a non en-US ones!
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        try {
            newDate = dateFormat.parse(tranDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFormat.format(newDate);
    }


	public String getClientId() {
		return clientId;
	}



  
}
