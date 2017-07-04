package org.springframework.jdbc.core;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

	//列名
	String value() default "";
	
	//数据库是�? 必填字段
	boolean required() default false;
	
	//字段长度
	int length() default 255;
}
