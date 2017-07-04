package org.springframework.jdbc.core;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Target(ElementType.TYPE)   //接口、类、枚举�?�注�?
@Retention(RetentionPolicy.RUNTIME)// 这种类型的Annotations将被JVM保留,�?以他们能在运行时被JVM或其他使用反射机制的代码�?读取和使�?.
public @interface Table {
	//表名
	 String value() default "";

}
