package com.dong.server.spring.service;

import java.util.List;

import com.dong.server.spring.entity.Hello;
import com.dong.server.spring.entity.Industry;

public interface IHello {

	void save(Hello hello);
	
	void savetwo(Hello hello);

	List<Hello> fetchAll(Hello hello);
	

	void update(Hello hello);

	void del(Hello hello);
}
