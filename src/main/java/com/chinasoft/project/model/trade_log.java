package com.chinasoft.project.model;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;


public class trade_log {
	int tid;
	String itcode;
	int account_change;
	int money;
	String log_time;
	String dept_name;
	//trade_type 红包雨/打赏/抢红包
	String trade_type;
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
	public String getLog_time() {
		return log_time;
	}
	public void setLog_time(String log_time) {
		this.log_time = log_time;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
}
