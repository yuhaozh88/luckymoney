package com.chinasoft.project.model;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

public class trade_log {
	int tid;
	String itcode;
	int account_change;
	int money;
	String logtime;
	String dept_name;
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public String getItcode() {
		return itcode;
	}
	public void setItcode(String itcode) {
		this.itcode = itcode;
	}
	public int getAccount_change() {
		return account_change;
	}
	public void setAccount_change(int account_change) {
		this.account_change = account_change;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public String getLogtime() {
		return logtime;
	}
	public void setLogtime(String logtime) {
		this.logtime = logtime;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	
}
