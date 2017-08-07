package com.chinasoft.project.model;

public class users_short_info {
	String itcode;
	String nickname;
	
	public users_short_info(String itcode, String nickname) {
		super();
		this.itcode = itcode;
		this.nickname = nickname;
	}
	public String getItcode() {
		return itcode;
	}
	public void setItcode(String itcode) {
		this.itcode = itcode;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
}
