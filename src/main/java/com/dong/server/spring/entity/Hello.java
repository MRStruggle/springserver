package com.dong.server.spring.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.jdbc.core.Column;
import org.springframework.jdbc.core.Table;

import com.fasterxml.jackson.annotation.JsonFormat;


@SuppressWarnings("serial")
@Table(value="hello")
public class Hello implements Serializable{


	private String createdate;
	
	public String getCreatedate() {
		return createdate;
	}


	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}


	@Column(value="name",required=true,length=80)
	private String name;
	
	@Column(value="createTime",required=true)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Timestamp createTime;
	
	@Column(value="id",required=true,length=44)
	private String id;


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Timestamp getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
