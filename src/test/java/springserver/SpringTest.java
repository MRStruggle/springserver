package springserver;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import com.dong.server.config.Config;

/**
 * 
 * SpringTest.java
 * ==============================================
 * Copy right 2017-2017  by http://www.bhnhz.com
 * ----------------------------------------------
 * This is not a free software, without any authorization is not allowed to use and spread.
 * ==============================================
 * @desc :  测试类 类标题， 类功能描述
 * @author: shenjd（shenjd@51barh.com）
 * @version: v1.0.0
 * @since: 2017年6月16日 下午10:03:58
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration(value = "com/dong/server")  
@ContextHierarchy({   
      @ContextConfiguration(name="parent",classes = Config.class)  
}) 
public class SpringTest {
	
	private String urlbase = "http://127.0.0.1:8080/";

	protected MockMvc mvc;

	private RequestBuilder requestBuilder;//请求串

	private String url;//请求url

	
	
	@Autowired
	private WebApplicationContext context;   //web 系统上下文


	@Before
	public void setupMockMvc() {
		//初始化
		mvc = MockMvcBuilders.webAppContextSetup(context).build();

	}

	@Test
	public void contextLoads() throws Exception {
		 
		String requestBody ="";
		
		 
		  url= urlbase+"hello/save";
		  requestBody= "{\"name\":\"测试数据库test\", \"createdate\": \"2017-07-04 13:43:00\"}";
		  
		 
		  url= urlbase+"hello/savedef";
		  requestBody= "{\"name\":\"测试默认数据库testtwo\", \"createdate\": \"2017-07-04 13:43:00\"}";
		  
		  url= urlbase+"hello/saveTwo";
		  requestBody= "{\"name\":\"测试数据库testtwo\", \"createdate\": \"2017-07-04 13:43:00\"}";
		  
		  url= urlbase+"hello/fetchAll";
		  requestBody= "{\"name\":\"测试数据库testtwo\", \"createdate\": \"2017-07-04 13:43:00\"}";
		  
		 // url= urlbase+"hello/update";
		 // requestBody= "{\"name\":\"更新200707数据库testtwo\", \"id\":\"2247ccb0609a11e7b06c00ffa6891afb\",\"createdate\": \"2017-07-05 18:43:00\"}";
		  
		  requestBuilder =fetchRequestBuilder(url,requestBody);



		  mvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andReturn().getResponse().getContentAsString();
		  
	}
	
	/**
	 * 
	 * SpringTest.java 控制器方法执行
	 * @author 笨东东
	 * @Since 2017年6月16日 下午10:22:51
	 * @param url    调用url地址,
	 * @param requestBody   请求参数    由于本项目请求参数为json  所以 发送post请求时 指定了json
	 * @return
	 */
	private RequestBuilder fetchRequestBuilder(String url,String requestBody){
		RequestBuilder requestBuilder=null;
		requestBuilder = MockMvcRequestBuilders.post(url,"json")
				.contentType(MediaType.APPLICATION_JSON)//设置请求头 为 application/json
				.content(requestBody)//设置请求参数
				.accept(MediaType.APPLICATION_JSON); //执行

		return requestBuilder;
	}
	

}
