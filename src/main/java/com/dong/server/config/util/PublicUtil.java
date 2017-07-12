package com.dong.server.config.util;

import static org.springframework.beans.BeanUtils.getPropertyDescriptor;
import static org.springframework.beans.BeanUtils.getPropertyDescriptors;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dong.server.spring.controller.BackStageTaskHz;

/**
 * 
 * PublicUtil.java
 * ==============================================
 * Copyright 2017-2017  by http://www.bhnhz.com
 * ----------------------------------------------
 * This is not a free software, without any authorization is not allowed to use and spread.
 * ==============================================
 * @desc :  公共方法 类标题， 类功能描述
 * @author: shenjd（shenjd@51barh.com）
 * @version: v1.0.0
 * @since: 2017年6月21日 下午3:47:50
 */
public class PublicUtil {

	private static Logger logger = Logger.getLogger(BackStageTaskHz.class);
	/**
     * @desc :    属性 复制  修改 spring 工具类
     * @author:  shenjd (shenjd@51lick.com)
     * @param:  [source    原对象,
     *          target     目标对象,
     *          editable    暂时不用 传入 null,
     *         ignoreProperties   不包含什么属性 转换
     *         ]
     * @return  void
     * @date: 2017/5/4 19:08
     */
    public static void copyProperties(Object source, Object target, Class<?> editable, String... ignoreProperties) throws BeansException {


        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        Class<?> actualEditable = target.getClass();
        if (editable != null) {
            if (!editable.isInstance(target)) {
                throw new IllegalArgumentException("Target class [" + target.getClass().getName() +
                        "] not assignable to Editable class [" + editable.getName() + "]");
            }
            actualEditable = editable;
        }
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;

        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null && (ignoreProperties == null || (!ignoreList.contains(targetPd.getName())))) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null &&
                            ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);
                            if(value != null){  //只拷贝不为null的属性 增加如下判断
                                if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                    writeMethod.setAccessible(true);
                                }
                                writeMethod.invoke(target, value);
                            }
                        }
                        catch (Throwable ex) {
                            throw new FatalBeanException(
                                    "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                        }
                    }
                }
            }
        }
    }
    
    
    public static Map<String,String> fetchMapByObjet_Class(Object obj){

        Map<String, String> RES = new HashMap<String, String>();
        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(obj.getClass());
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = property.getReadMethod();
            Object value = null;
            try {
                value = getter != null ? getter.invoke(obj) : null;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            RES.put(key, value==null?"":String.valueOf(value));
        }

        return RES;
    }
    
    /**
     * 保存文件
     * PublicUtil.java 方法的描述
     * @author 笨东东
     * @Since 2017年7月12日 上午9:46:11
     * @param file
     * @param request
     * @return
     */
    public static boolean saveFile(MultipartFile file,HttpServletRequest request) {  
        // 判断文件是否为空  
        if (file != null && !file.isEmpty()) {  
            try {  
                // 文件保存路径  
            	String fileproPath = request.getSession().getServletContext().getRealPath("/") ;
        		File filedir = new File(fileproPath);
                String parpatch = filedir.getParent();
            	
            	
            	String filePath = parpatch + "/upload/";
            	judeDirExists(new File(filePath));	
            	String name = new String(file.getOriginalFilename().getBytes("gbk"),"utf-8");
                String filenamPath = filePath  + name;  
                // 转存文件  
                file.transferTo(new File(filenamPath));  
                return true;  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return false;  
    } 
    
    
 
    /**
     * 判断文件夹是否存在
     * PublicUtil.java 方法的描述
     * @author 笨东东
     * @Since 2017年7月12日 上午9:52:26
     * @param file
     */
	public static void judeDirExists(File file) {

		if (file.exists()) {
			if (file.isDirectory()) {
				logger.info("dir exists");
			} else {
				logger.info("the same name file exists, can not create dir");
			}
		} else {
			logger.info("dir not exists, create it ...");
			file.mkdir();
		}

	}
    
	/**
	 * 下载文件 
	 * PublicUtil.java 方法的描述
	 * @author 笨东东
	 * @Since 2017年7月12日 上午11:53:43
	 * @param fileName
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static void download(String fileName,HttpServletRequest request,HttpServletResponse response) throws IOException {    
		 
		String filePath = request.getSession().getServletContext().getRealPath("/") ;
		File filedir = new File(filePath);
        String parpatch = filedir.getParent();
		
		String fileNamePath = parpatch + "/upload/" + fileName;  
		File file = new File(fileNamePath);
		
		String userAgent = request.getHeader("User-Agent");
        boolean isIE = (userAgent != null) && (userAgent.toLowerCase().indexOf("msie") != -1);

        response.reset();
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "must-revalidate, no-transform");
        response.setDateHeader("Expires", 0L);

        response.setContentType("application/x-download");
        response.setContentLength((int) file.length());
		
        
        if (isIE) {
        	fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
        } else {
        	fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        }
        
        BufferedInputStream is = null;
        OutputStream os = null;
        try {

            os = response.getOutputStream();
            is = new BufferedInputStream(new FileInputStream(file));
            IOUtils.copy(is, os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is);
        }
        
//        HttpHeaders headers = new HttpHeaders();    
//        
//        headers.setContentDispositionFormData("attachment", fileName);   
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
//        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),    
//                                          headers, HttpStatus.CREATED);   
                                          
                                          
    } 
    
	/**
	 * 校验文件是否存在
	 * PublicUtil.java 方法的描述
	 * @author 笨东东
	 * @Since 2017年7月12日 下午1:22:20
	 * @param filePath
	 * @return
	 */
	public static String checkfileISExist(String filename,HttpServletRequest request) {
		
		String filePath = request.getSession().getServletContext().getRealPath("/") ;//+ ;
		File file = new File(filePath);
        String parpatch = file.getParent();
        
        File fileesists = new File(parpatch+"/upload/"+filename );
        
		if (!fileesists.exists() || !fileesists.canRead()) {
			return "您下载的文件不存在！";
		}
		return null;
	}
}
