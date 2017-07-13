package com.dong.server.config.util;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ser.CustomSerializerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.codehaus.jackson.JsonGenerator;  
import org.codehaus.jackson.JsonProcessingException;  
import org.codehaus.jackson.map.JsonSerializer;  
import org.codehaus.jackson.map.ObjectMapper;  
import org.codehaus.jackson.map.SerializerProvider;  
import org.codehaus.jackson.map.ser.CustomSerializerFactory;  



public class JsonUtil extends ObjectMapper {
	
	public static  objMapper = new ObjectMapper();
	public static Gson gson=new GsonBuilder().disableHtmlEscaping().create(); //防止某个值包含有=，会变为\u003d的情�?
    private static Type type;
    public JsonUtil(){
        if(gson==null){
            gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")//Configures Gson to to serialize Date objects according to the style value provided
                    .setVersion(2.1)
                    .serializeNulls()//Configure Gson to serialize null fields
                    .create();
        }
        
        CustomSerializerFactory factory = new CustomSerializerFactory();  
    	factory.addGenericMapping(Date.class, new JsonSerializer<Date>(){  
            @Override  
            public void serialize(Date value,   
                    JsonGenerator jsonGenerator,   
                    SerializerProvider provider)  
                    throws IOException, JsonProcessingException {  
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
                jsonGenerator.writeString(sdf.format(value));  
            }  
        });   
        this.setSerializerFactory(factory);  
    }

    /**
     * 将对象转换成json格式
     *
     */
    public static String objectToJson(Object ts) {
        String jsonStr = null;
        if (gson != null) {
            jsonStr = gson.toJson(ts);
        }
        return jsonStr;
    }

    /**
     * 将json格式转换成list对象
     *
     * @param jsonStr
     * @return
     */
    public static List<?> jsonToList(String jsonStr) {
        List<?> objList = null;
        if (gson != null) {
            java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<?>>() {
            }.getType();
            objList = gson.fromJson(jsonStr, type);
        }
        return objList;
    }

    /**
     * 将json格式转换成list对象，并准确指定类型
     * @param jsonStr
     * @param type
     * @return
     */
    public static List<?> jsonToList(String jsonStr, java.lang.reflect.Type type) {
        List<?> objList = null;
        if (gson != null) {
            objList = gson.fromJson(jsonStr, type);
        }
        return objList;
    }

    /**
     * 将json格式转换成map对象
     *
     * @param jsonStr
     * @return
     */
    public static Map<?, ?> jsonToMap(String jsonStr) {
        Map<?, ?> objMap = null;
        if (gson != null) {
            java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<Map<?, ?>>() {
            }.getType();
            objMap = gson.fromJson(jsonStr, type);
        }
        return objMap;
    }

//    /**
//     * 将json转换成bean对象
//     *
//     * @param jsonStr
//     * @return
//     */
//    public static Object jsonToBean(String jsonStr, Class<?> cl) {
//        Object obj = null;
//        if (gson != null) {
//            obj = gson.fromJson(jsonStr, cl);
//        }
//        return obj;
//    }
//
//
//    /**
//     * 设置转换类型
//     */
//    public static String toJson(Object date){
//        type=new TypeToken<Object>() {}.getType();
//        return gson.toJson(date, type);
//    }

    /**
     * 设置转换类型
     */
    public static Object fromJson(String json,Type rtype){
        return gson.fromJson(json, rtype);
    }
	
/*	public static ObjectMapper objMapper = new ObjectMapper();

	static {
		// 解决序列化的时�?? 去掉 为null�? 字段
		objMapper.setSerializationInclusion(Include.NON_NULL);
		
//		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		objMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));  
		// 是否对属性使用排序，默认排序按照字母顺序�?
		//objMapper.configure(SerializationConfig.Feature.SORT_PROPERTIES_ALPHABETICALLY, false);
		
		//忽略�?些属�?
		//objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
//		DeserializationConfig cfg = objMapper.getDeserializationConfig();  
//		//设置JSON时间格式    
//		SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
//		  
//		DateFormat ss = cfg.getDateFormat();
		
	}

	*//**
	 * obj转json字符�?.
	 * 
	 * @param obj
	 * @return
	 *//*
	public String toJson(Object obj) {
		String res = null;
		try {

			res = objMapper.writeValueAsString(obj);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return res;
	}

	*//**
	 * json转obj.
	 * 
	 * @param json
	 * @param cls
	 * @return
	 * @throws Exception
	 *//*
	public Object jsonToBean(String json, Class<?> cls) {
		Object obj = null;
		try {
			obj = objMapper.readValue(json, cls);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return obj;
	}*/
    
    
    

}
