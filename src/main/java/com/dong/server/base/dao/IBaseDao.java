package com.dong.server.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.jdbc.core.SqlProviderHz;
import org.springframework.stereotype.Component;

/**
 * 
 * IBaseDao.java
 *  ============================================== 
 * Copyright 2017-2017 by http://www.bhnhz.com
 * ---------------------------------------------- This is not a free software,
 * without any authorization is not allowed to use and spread.
 * ==============================================
 * 
 * @desc : TODO 类标题， 类功能描�?
 * @author: shenjd（shenjd@51barh.com�?
 * @version: v1.0.0
 * @since: 2017�?6�?16�? 上午10:35:07
 */

public interface IBaseDao<T> {

	/**
	 * 
	 * 新增数据方法
	 * 
	 * @author 笨东�?
	 * @Since 2017�?6�?16�? 上午10:28:03
	 * @param bean
	 * @return
	 */
	@InsertProvider(type = SqlProviderHz.class, method = "insert")
	///@Options(useGeneratedKeys = true,keyProperty="Id")
	@SelectKey(statement = "select REPLACE(UUID(),'-','')", keyProperty = "id", before = true, resultType = String.class)
	public int insert(T bean);

	/**
	 * 
	 * 删除数据方法
	 * 
	 * @author 笨东�?
	 * @Since 2017�?6�?16�? 上午10:35:46
	 * @param bean
	 * @return
	 */
	@DeleteProvider(type = SqlProviderHz.class, method = "delete")
	public int delete(T bean);

	/**
	 * 更新数据方法
	 * 
	 * @author 笨东�?
	 * @Since 2017�?6�?16�? 上午10:36:25
	 * @param bean
	 * @return
	 */
	@UpdateProvider(type = SqlProviderHz.class, method = "update")
	public int update(T bean);

	/**
	 * 
	 * 查询方法
	 * @author 笨东�?
	 * @Since 2017�?6�?16�? 下午5:09:12
	 * @param bean
	 * @return
	 */
	@SelectProvider(type = SqlProviderHz.class, method = "fetchOne")
	public T fetchOne(T bean);
	
	/**
	 * 查询�?�?  单表操作
	 * IBaseDao.java 方法的描�?
	 * @author 笨东�?
	 * @Since 2017�?6�?20�? 下午2:24:19
	 * @param bean
	 * @return
	 */
	@SelectProvider(type = SqlProviderHz.class, method = "fetchAll")
	public List<T> fetchAllObj(T bean);
	
	
	

}
