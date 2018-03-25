package com.monitoring.entity;

import java.io.Serializable;

import mtime.kits.excel.annotation.ExcelField;

public class LoginLogExcelBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@ExcelField(title = "Account", sort = 0, align = 2,width=5000)
	private String accountName;
	@ExcelField(title = "Login time", sort = 1, align = 2,width=5000)
	private String loginTime;
	@ExcelField(title = "Login IP", sort = 2, align = 2,width=8000)
	private String loginIP;
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	public String getLoginIP() {
		return loginIP;
	}
	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}
	
}
