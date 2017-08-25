package com.dong.server.spring.entity;

import java.io.Serializable;

public class Industry implements Serializable{
	
	private String userid;
	private String industry;
	private String sort;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	
	
	
	

}
