package com.dong.server.config;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * 
 * WebInitializerHz.java
 * ==============================================
 * Copyright 2017-2017  by http://www.bhnhz.com
 * ----------------------------------------------
 * This is not a free software, without any authorization is not allowed to use and spread.
 * ==============================================
 * @desc :  SPRING WEB 容器启动注解�?  类标题， 类功能描�?  或�?? 集成 WebApplicationInitializer 
 * @author: shenjd（shenjd@51barh.com�?
 * @version: v1.0.0
 * @since: 2017�?6�?19�? 上午9:21:38
 */
public class WebInitializerHz extends AbstractAnnotationConfigDispatcherServletInitializer {

	 /**
     * 该方法定义的是全�?�? applicationContext
     * 在初始化时需要去扫描哪些带注解（如：@Configuration）的�?
     */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[] { Config.class };
	}

	 /**
     * 该方法定义的时Servlet级别的applicationContext
     * 在初始化时需要去扫描哪些带注解（如：@Configuration）的�?
     */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
     * 定义 DispatcherServlet 拦截的路�?
     */
	@Override
	protected String[] getServletMappings() {
		
		return new String[] { "/" };
	}
	
	
	protected void customizeRegistration(Dynamic registration) {
		// TODO Auto-generated method stub
		super.customizeRegistration(registration);
		
		registration.setMultipartConfig(getMultipartConfigElement());
	}
	
	private MultipartConfigElement getMultipartConfigElement() {
		MultipartConfigElement multipartConfigElement = new MultipartConfigElement(	LOCATION, MAX_FILE_SIZE, MAX_REQUEST_SIZE, FILE_SIZE_THRESHOLD);
		return multipartConfigElement;
	}

	private static final String LOCATION = "C:/temp/"; // Temporary location where files will be stored

	private static final long MAX_FILE_SIZE = 5242880; // 5MB : Max file size.
														// Beyond that size spring will throw exception.
	private static final long MAX_REQUEST_SIZE = 20971520; // 20MB : Total request size containing Multi part.
	
	private static final int FILE_SIZE_THRESHOLD = 0; // Size threshold after which files will be written to disk


}
