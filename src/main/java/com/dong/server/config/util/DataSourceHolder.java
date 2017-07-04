package com.dong.server.config.util;

public class DataSourceHolder {

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
	    
	
}
