package com.monitoring.entity;

import java.io.Serializable;

import mtime.kits.excel.annotation.ExcelField;

public class OptLogExeclBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@ExcelField(title = "Account", sort = 0, align = 2,width=5000)
	private String accountName;
	@ExcelField(title = "Module", sort = 1, align = 2,width=3000)
	private String module;
	@ExcelField(title = "Operation type", sort = 2, align = 2,width=8000)
	private String methods;
	@ExcelField(title = "Response time", sort = 3, align = 2,width=8000)
	private String actionTime;
	@ExcelField(title = "IP address", sort = 4, align = 2,width=8000)
	private String userIP;
	@ExcelField(title = "Excution time", sort = 5, align = 2,width=8000)
	private String operTime;
	@ExcelField(title = "Excution description", sort = 6, align = 2,width=8000)
	private String description;
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getMethods() {
		return methods;
	}
	public void setMethods(String methods) {
		this.methods = methods;
	}
	public String getActionTime() {
		return actionTime;
	}
	public void setActionTime(String actionTime) {
		this.actionTime = actionTime;
	}
	public String getUserIP() {
		return userIP;
	}
	public void setUserIP(String userIP) {
		this.userIP = userIP;
	}
	public String getOperTime() {
		return operTime;
	}
	public void setOperTime(String operTime) {
		this.operTime = operTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
