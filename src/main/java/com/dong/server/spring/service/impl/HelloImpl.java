package com.dong.server.spring.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dong.server.spring.dao.HelloDao;
import com.dong.server.spring.entity.Hello;
import com.dong.server.spring.service.IHello;

@Service
@Transactional
public class HelloImpl implements IHello {

	@Autowired
	private HelloDao helloDao;
	
	public void save(Hello hello){
		helloDao.insert(hello);
		//BigDecimal.ONE.divide(BigDecimal.ZERO);
	}
	
	public void savetwo(Hello hello){
		helloDao.insert(hello);
	}

	@Override
	public List<Hello> fetchAll(Hello hello) {
		// TODO Auto-generated method stub
		return helloDao.fetchAllObj(hello);
	}


	
	
}
