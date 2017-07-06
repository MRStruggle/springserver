package org.springframework.jdbc.core;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.apache.log4j.Logger;


/**
 * 
 * SqlProviderHz.java
 * ==============================================
 * Copyright 2017-2017  by http://www.bhnhz.com
 * ----------------------------------------------
 * This is not a free software, without any authorization is not allowed to use and spread.
 * ==============================================
 * @desc : sql语句 生成方法
 * @author: shenjd（shenjd@51barh.com）
 * @version: v1.0.0
 * @since: 2017年6月16日 上午10:36:52
 */
public class SqlProviderHz {

	private Logger logger = Logger.getLogger(SqlProviderHz.class);

	/**
	 * 
	 * 插入
	 * 
	 * @author 笨东东
	 * @Since 2017年6月16日 上午10:46:50
	 * @param info
	 * @return
	 */
	public String insert(Object info) {
		Class<?> beanClass = info.getClass();
		logger.info("执行插入数据方法开始, " + beanClass.getName());

		String tablename = getTableName(beanClass);

		Map<String, List<String>> map = getfieldAndValue(info);
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ").append(tablename).append("(");
		List<String> field = map.get("field");
		List<String> fieldvalue = map.get("fieldvalue");
		for (int i = 0; i < field.size(); i++) {
			sql.append(field.get(i));
			if (i != field.size() - 1)
				sql.append(",");
		}
		sql.append(")").append(" VALUES(");
		for (int i = 0; i < fieldvalue.size(); i++) {
			sql.append(fieldvalue.get(i));
			if (i != fieldvalue.size() - 1)
				sql.append(",");
		}
		sql.append(")");
		logger.info("get insert sql 为:  " + sql);
		return sql.toString();
		// return new SQL() {
		// {
		// INSERT_INTO("article");
		// VALUES("articeId", "#{articeId}");
		// VALUES("articeNam", "#{articeNam}");
		// VALUES("articeNum", "#{articeNum}");
		// }
		// }.toString();

	}

	/**
	 * 
	 * 更新
	 * 
	 * @author 笨东东
	 * @Since 2017年6月16日 上午10:47:02
	 * @param info
	 * @return
	 */
	public String update(Object info) {
		
		 Class<?> beanClass = info.getClass();
	        String tableName = getTableName(beanClass);
	        Field[] fields = getFields(beanClass);
	        StringBuilder updateSql = new StringBuilder();
	        updateSql.append(" update ").append(tableName).append(" set ");
	        try {
	            for (int i = 0; i < fields.length; i++) {
	                Field field = fields[i];
	                Column column = field.getAnnotation(Column.class);
	                String columnName = "";
	                if (column != null) {
	                    if (!column.required())
	                        continue;
	                    columnName = column.value();
	                    
	                    field.setAccessible(true);
		                Object beanValue = field.get(info);
		                if (beanValue != null && !columnName.equals("id")) {
		                    updateSql.append(columnName).append("=#{").append(field.getName()).append("}");
		                    if (i != fields.length - 1) {
		                        updateSql.append(",");
		                    }
		                }
	                }
	               
	                
	            }
	        } catch (Exception e) {
	            new RuntimeException("get update sql is exceptoin:" + e);
	        }
	        
	        if(updateSql.toString().endsWith(",")){
	        	String sub = updateSql.substring(0, updateSql.length()-1);
	        	logger.info("get update sql 为： " +sub+ " where id =#{id}");
	        	return sub+ " where id =#{id} ";
	        }else{
	        	 updateSql.append(" where ").append(" id =#{id} ");
	        }
	        logger.info("get update sql 为： " +updateSql.toString());
	        return updateSql.toString();
//		Class<?> beanClass = info.getClass();
//		logger.info("执行更新数据方法开始, " + beanClass.getName());
//		String tableName = getTableName(beanClass);
//		Field[] fields = getFields(beanClass);
//		//StringBuilder updateSql = new StringBuilder();
//		//updateSql.append(" update ").append(tableName).append(" set ");
//		SQL sql =null;
//		try {
//			
//			sql =  new SQL(){
//				{
//					update(tableName);
//					SET(fields.toString());
//					for (int i = 0; i < fields.length; i++) {
//						Field field = fields[i];
//						Column column = field.getAnnotation(Column.class);
//						String columnName = "";
//						if (column != null) {
//							if (!column.required())
//								continue;
//							columnName = column.value();
//							
//							
//							field.setAccessible(true);
//							Object beanValue = field.get(info);
//							if (beanValue != null && !columnName.equals("Id")) {
//								
//								SET(columnName+"=#{"+field.getName()+"}" );
//							}
//						}
//					}
//					WHERE("Id=#{Id}");
//				}
//			};
//			
//		} catch (Exception e) {
//			new RuntimeException("get update sql is exceptoin: " + e);
//		}
//		logger.info("get update sql 为： " + sql.toString());
//		return sql.toString();
	}

	/**
	 * 
	 * 删除方法
	 * 
	 * @author 笨东东
	 * @Since 2017年6月16日 上午10:47:09
	 * @param bean
	 * @return
	 */
	public String delete(Object bean) {
		Class<?> beanClass = bean.getClass();
		logger.info("执行删除数据方法开始, " + beanClass.getName());
		String tableName = getTableName(beanClass);
		Field[] fields = getFields(beanClass);
		StringBuilder deleteSql = new StringBuilder();
		deleteSql.append(" delete from ").append(tableName).append(" where  ");
		try {
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				Column column = field.getAnnotation(Column.class);
				String columnName = "";
				if (column != null) {
					if (!column.required())
						continue;
					columnName = column.value();
				}

				field.setAccessible(true);
				Object beanValue = field.get(bean);
				if (beanValue != null) {
					deleteSql.append(columnName).append("=#{").append(field.getName()).append("}");
					if (i != fields.length - 1) {
						deleteSql.append(" and ");
					}
				}
			}
		} catch (Exception e) {
			new RuntimeException("get delete sql is exceptoin: " + e);
		}
		logger.info("get delete sql 为：" + deleteSql.toString());
		return deleteSql.toString();
	}

	/**
	 * 
	 * 查询方法
	 * 
	 * @author 笨东东
	 * @Since 2017年6月16日 上午10:47:09
	 * @param bean
	 * @return
	 */
	public String fetchOne(Object bean) {
		Class<?> beanClass = bean.getClass();
		logger.info("执行查询数据方法开始," + beanClass.getName());
		String tableName = getTableName(beanClass);
		Map<String, List<String>> map = getfieldAndValue(bean);
		StringBuilder selectSql = new StringBuilder(" select * ");
		List<String> field = map.get("field");
		List<String> fieldvalue = map.get("fieldvalue");
		selectSql.append(" from ").append(tableName).append(" where 1=1 ");
		for (int j = 0; j < fieldvalue.size(); j++) {
			selectSql.append(" and ").append(field.get(j)).append("=").append(fieldvalue.get(j));
			
		}
	
		logger.info("get select sql 为：" + selectSql);
		
		return selectSql.toString();
	}
	
	/**
	 * 根据注解 获取 字段名 和字段值 SqlProvider.java 方法的描述
	 * 
	 * @author 笨东东
	 * @Since 2017年6月14日 下午9:20:40
	 * @param bean
	 * @return
	 */
	public Map<String, List<String>> getfieldAndValue(Object bean) {
		Class<?> beanClass = bean.getClass();
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		Field[] fields = getFields(beanClass);
		List<String> Paras = new ArrayList<String>();
		List<String> ParaNames = new ArrayList<String>();
		try {
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				Column column = field.getAnnotation(Column.class);
				String columnName = "";
				if (column != null) {
					if (!column.required())
						continue;
					columnName = column.value();
					
					
					field.setAccessible(true);
					Object object = field.get(bean);
					if (object != null) {
						ParaNames.add(columnName);
						Paras.add("#{" + field.getName() + "}");
					}
				}

				
			}
		} catch (Exception e) {
			new RuntimeException(e);
		}
		map.put("field", ParaNames);
		map.put("fieldvalue", Paras);

		return map;
	}

	/**
	 * 获取表名 SqlProvider.java 方法的描述
	 * 
	 * @author 笨东东
	 * @Since 2017年6月14日 下午8:58:47
	 * @param obj
	 * @return
	 */
	private String getTableName(Class<?> beanClass) {

//		String tableName = "";
		Table table = beanClass.getAnnotation(Table.class);
		return table.value();

	}

	/**
	 * 获取类属性 SqlProvider.java 方法的描述
	 * 
	 * @author 笨东东
	 * @Since 2017年6月14日 下午8:57:58
	 * @param beanClass
	 * @return
	 */
	private Field[] getFields(Class<?> beanClass) {
		Field[] beanFields = beanClass.getDeclaredFields();
		ArrayList<Field> fields = new ArrayList<Field>();
		if (beanFields != null && beanFields.length > 0) {
			for (int i = 0; i < beanFields.length; i++) {
				Field field = beanFields[i];
				Column column = field.getAnnotation(Column.class);
				
				if (column != null) {
					if (!column.required())
						continue;
					
					fields.add(beanFields[i]);
				}
				
			}
		}
		Class<?> beanSuperClass = beanClass.getSuperclass();
		Field[] beanSuperFields = beanSuperClass.getDeclaredFields();
		if (beanSuperFields != null && beanSuperFields.length > 0) {
			for (int i = 0; i < beanSuperFields.length; i++) {
				Field field = beanSuperFields[i];
				Column column = field.getAnnotation(Column.class);
				
				if (column != null) {
					if (!column.required())
						continue;
				fields.add(beanSuperFields[i]);
				}
			}
		}
		return (Field[]) fields.toArray(new Field[fields.size()]);
	}
	
	
	/**
	 * 查询所有数据
	 * SqlProviderHz.java 方法的描述
	 * @author 笨东东
	 * @Since 2017年6月20日 下午2:28:16
	 * @param info
	 * @return
	 */
	public String fetchAll(Object info){
		
		Class<?> beanClass = info.getClass();
		logger.info("执行查询所有数据的方法开始, " + beanClass.getName());

		String tablename = getTableName(beanClass);
		 return new SQL() {
				 {
				 SELECT(" * ");
				 FROM(tablename);
				 ORDER_BY("createTime");
				 }
				 }.toString();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
