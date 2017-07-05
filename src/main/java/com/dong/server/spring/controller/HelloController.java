package com.dong.server.spring.controller;

import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataSource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dong.server.api.ServiceResponse;
import com.dong.server.spring.entity.Hello;
import com.dong.server.spring.service.IHello;


@RestController
@RequestMapping("/hello")
@CrossOrigin
public class HelloController {

	private Logger logger = Logger.getLogger(HelloController.class);
	
	@Autowired
	private IHello helloimpl;
	
	/**
	 * 指定 @DataSource  走dataSource数据源
	 * @param hello
	 * @return
	 */
	@DataSource(value="dataSource")
	@RequestMapping(value="save")//,method= RequestMethod.POST
	public ServiceResponse save(@RequestBody Hello hello){
		
		hello.setCreateTime(Timestamp.valueOf(hello.getCreatedate()));
		helloimpl.save(hello);
		
		return ServiceResponse.getInstance("0", "ok");
	}
	
	/**
	 * 指定 @DataSource  走dataSource2数据源
	 * @param hello
	 * @return
	 */
	@DataSource(value="dataSource2")
	@RequestMapping(value="saveTwo",method= RequestMethod.POST)
	public ServiceResponse savetwo(@RequestBody Hello hello){
		hello.setCreateTime(Timestamp.valueOf(new Timestamp(System.currentTimeMillis()).toString()));
		helloimpl.savetwo(hello);
		return ServiceResponse.getInstance("0", "ok");
	}
	
	
	/**
	 * 不指定 @DataSource  走默认数据源
	 * @param hello
	 * @return
	 */
	@RequestMapping(value="savedef",method= RequestMethod.POST)
	public ServiceResponse savedef(@RequestBody Hello hello){
		
		hello.setCreateTime(Timestamp.valueOf(hello.getCreatedate()));
		helloimpl.save(hello);
		
		return ServiceResponse.getInstance("0", "ok");
	}
	
	@DataSource(value="dataSource2")
	@RequestMapping(value="fetchAll",method= RequestMethod.POST)
	public ServiceResponse fetchAll(){
		
		List<Hello> hellos = helloimpl.fetchAll(new Hello());
		for(Hello h : hellos){
			h.setCreatedate(h.getCreateTime().toString());
		}
		return ServiceResponse.getInstance("0", hellos);
	}
	
}
