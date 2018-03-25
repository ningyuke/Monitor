package com.monitoring.entity;

import java.io.Serializable;

import mtime.kits.excel.annotation.ExcelField;

public class ServerInfoExcelBean implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	@ExcelField(title = "Warining type", sort = 0, align = 2,width=5000)
	private String type;
	@ExcelField(title = "Preset value", sort = 1, align = 2,width=3000)
	private String threshold;
	@ExcelField(title = "Monitoring value", sort = 2, align = 2,width=8000)
	private String currentValue;
	@ExcelField(title = "Email", sort = 3, align = 2,width=8000)
	private String email;
	@ExcelField(title = "Email content", sort = 4, align = 2,width=8000)
	private String emailText;
	@ExcelField(title = "Warining time", sort = 5, align = 2,width=8000)
	private String operTime;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getThreshold() {
		return threshold;
	}
	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}
	public String getCurrentValue() {
		return currentValue;
	}
	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmailText() {
		return emailText;
	}
	public void setEmailText(String emailText) {
		this.emailText = emailText;
	}
	public String getOperTime() {
		return operTime;
	}
	public void setOperTime(String operTime) {
		this.operTime = operTime;
	}
	
}
