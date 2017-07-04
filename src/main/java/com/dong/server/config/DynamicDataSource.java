package com.dong.server.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.dong.server.config.util.DataSourceHolder;

public class DynamicDataSource extends AbstractRoutingDataSource {

	
	 private static final ThreadLocal<String> contextHolder = new InheritableThreadLocal<String>(); 
	    /**
	     * @Description: 设置数据源类�?
	     * @param dataSourceType  数据库类�?
	     * @return void
	     * @throws
	     */ 
	    public static void setDataSourceType(String dataSourceType) { 
	        contextHolder.set(dataSourceType); 
	    } 
	     
	    /**
	     * @Description: 获取数据源类�?
	     * @param 
	     * @return String
	     * @throws
	     */ 
	    public static String getDataSourceType() { 
	        return contextHolder.get(); 
	    } 
	     
	    /**
	     * @Description: 清除数据源类�?
	     * @param 
	     * @return void
	     * @throws
	     */ 
	    public static void clearDataSourceType() { 
	        contextHolder.remove(); 
	    }
	
	
	
	
	protected Object determineCurrentLookupKey() {
		// TODO Auto-generated method stub
		return contextHolder.get();  
	}
	
	
	
	
}
