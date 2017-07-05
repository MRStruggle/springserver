package com.dong.server.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.jdbc.core.DataSource;
import org.springframework.stereotype.Component;

import com.dong.server.config.DynamicDataSource;

@Aspect
@Component
public class DataSourceAspect {//implements MethodBeforeAdvice, AfterReturningAdvice

	
	/**
     * 定义拦截规则：拦截包下面的所有类
     */
    @Pointcut("execution(* com.dong.server.spring.controller.*.*(..))")
    public void MethodPointcut(){}
    
    
	@AfterReturning("MethodPointcut()")
	public void afterReturning() throws Throwable {
		// TODO Auto-generated method stub
		DynamicDataSource.clearDataSourceType();
	}
	
//	@AfterReturning("MethodPointcut()")
//	public void after(JoinPoint joinPoint) {
//		if (TransactionSynchronizationManager.isActualTransactionActive())
//			return;
//		if (TransactionSynchronizationManager.isSynchronizationActive())
//			TransactionSynchronizationManager.clearSynchronization();
//		DynamicDataSource.clearDataSourceType();
//	}

	@Before(value="MethodPointcut()")
	public void before(JoinPoint point) throws Throwable {
		Class<?> target = point.getTarget().getClass();
		MethodSignature signature = (MethodSignature) point.getSignature();
		// 默认使用目标类型的注解，如果没有则使用其实现接口的注解
		for (Class<?> clazz : target.getInterfaces()) {
			resolveDataSource(clazz, signature.getMethod());
		}
		resolveDataSource(target, signature.getMethod());
	}
	
	/**
	 * 提取目标对象方法注解和类型注解中的数据源标识
	 * 
	 * @param clazz
	 * @param method
	 */
	private void resolveDataSource(Class<?> clazz, Method method) {
		try {
			Class<?>[] types = method.getParameterTypes();
			// 默认使用类型注解
			if (clazz.isAnnotationPresent(DataSource.class)) {
				DataSource source = clazz.getAnnotation(DataSource.class);
				DynamicDataSource.setDataSourceType(source.value());
			}
			// 方法注解可以覆盖类型注解
			Method m = clazz.getMethod(method.getName(), types);
			if (m != null && m.isAnnotationPresent(DataSource.class)) {
				DataSource source = m.getAnnotation(DataSource.class);
				DynamicDataSource.setDataSourceType(source.value());
			}
		} catch (Exception e) {
			System.out.println(clazz + ":" + e.getMessage());
		}
	}

}
