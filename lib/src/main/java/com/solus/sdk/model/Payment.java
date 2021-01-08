package com.solus.sdk.model;

public class Payment {
    
	
	private String apiKey;
    private String pan;
    private String expDate;
    private String ipin;
    private Float tranAmount;
    private String payeeId;
    private String serviceProviderId;

	public String getPayeeId() {
		return payeeId;
	}

	public void setPayeeId(String payeeId) {
		this.payeeId = payeeId;
	}

	public String getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(String paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

	private String paymentInfo;

	public types getBillerId() {
		return billerId;
	}

	public void setBillerId(types billerId) {
		this.billerId = billerId;
	}

	private types billerId;

	
	public Payment() {
		
	}

	public Payment(String apiKey, String pan, String expDate, String ipin, Float tranAmount , String serviceProviderId) {
		this.apiKey = apiKey;
		this.pan = pan;
		this.expDate = expDate;
		this.ipin = ipin;
		this.tranAmount = tranAmount;
		this.serviceProviderId = serviceProviderId;
	}

	public Payment(String apiKey, String pan, String expDate, String ipin, Float tranAmount, types billerId, String paymentInfo , String serviceProviderId) {
		this.apiKey = apiKey;
		this.pan = pan;
		this.expDate = expDate;
		this.ipin = ipin;
		this.tranAmount = tranAmount;
		this.serviceProviderId = serviceProviderId;
		/*
		this.billerId = billerId;
		this.paymentInfo = paymentInfo;
		*
		const (
			zain   = "0010010001"
			mtn    = "0010010003"
			sudani = "0010010005"
			nec    = "0010020001"
		)
		 */
		if (billerId == types.E15) {
			this.payeeId = Constants.e15;
			this.paymentInfo = paymentInfo;

		}else if (billerId == types.MTNTopUp) {
			this.payeeId = Constants.mtn;
			this.paymentInfo = "MPHONE=" + paymentInfo;

		}else if (billerId == types.SudaniTopUp) {
			this.payeeId = Constants.sudani;
			this.paymentInfo = "MPHONE=" + paymentInfo;

		} else if (billerId == types.ZainTopUp) {
				this.payeeId = Constants.zain;
				this.paymentInfo = "MPHONE=" + paymentInfo;

		}else if (billerId == types.NEC) {
			this.payeeId = Constants.nec;
			this.paymentInfo = "METER=" + paymentInfo;
		}


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

	public String getServiceProviderId() {
		return serviceProviderId;
	}

	public void setServiceProviderId(String serviceProviderId) {
		this.serviceProviderId = serviceProviderId;
	}
	

}

class Constants {
	public static String zain = "0010010001";
	public static String mtn = "0010010003";
	public static String sudani = "0010010005";
	public static String e15 = "0010050001";
	public static String nec = "0010020001";
}
