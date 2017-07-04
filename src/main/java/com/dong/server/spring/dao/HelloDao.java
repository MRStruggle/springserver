package com.dong.server.spring.dao;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.jdbc.core.DataSource;
import org.springframework.jdbc.core.SqlProviderHz;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dong.server.base.dao.IBaseDao;
import com.dong.server.spring.entity.Hello;

@Component
@Transactional
public interface HelloDao extends IBaseDao<Hello>{

}
