package com.dong.server.api;


/**
 * 
 * ServiceResponse.java
 * ==============================================
 * Copyright 2017-2017  by http://www.bhnhz.com
 * ----------------------------------------------
 * This is not a free software, without any authorization is not allowed to use and spread.
 * ==============================================
 * @desc :  响应类 
 * @author: shenjd（shenjd@51barh.com�?
 * @version: v1.0.0
 * @since: 2017年6月16日下午9:46:41
 */
public class ServiceResponse {

   
    private String state;          //返回成功 或�?�是�? 
    private Object data;             //返回消息体Data信息

    
    
    public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	 public  static ServiceResponse getInstance(String state,Object items) {
		 ServiceResponse serviceResponse = new ServiceResponse();
		 serviceResponse.setState(state);
		 serviceResponse.setData(items);
		 
		 return serviceResponse;
	 }

	


    

}
