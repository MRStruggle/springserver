package com.dong.server.spring.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.dong.server.api.ServiceResponse;
import com.dong.server.config.util.JsonUtil;
import com.dong.server.config.util.PublicUtil;
import com.dong.server.spring.entity.Hello;
import com.dong.server.spring.service.IHello;


@Controller
@RequestMapping("/hello")
//@CrossOrigin
public class HelloController {

	private Logger logger = Logger.getLogger(HelloController.class);
	
	@Autowired
	private IHello helloimpl;
	
	/**
	 * 指定 @DataSource  走dataSource数据源
	 * @param hello
	 * @return
	 */
	@DataSource(value="dataSource")
	@RequestMapping(value="save")//,method= RequestMethod.POST
	public ServiceResponse save(@RequestBody Hello hello){
		
		hello.setCreateTime(Timestamp.valueOf(hello.getCreatedate()));
		helloimpl.save(hello);
		
		return ServiceResponse.getInstance("0", "ok");
	}
	
	/**
	 * 指定 @DataSource  走dataSource2数据源
	 * @param hello
	 * @return
	 */
	@DataSource(value="dataSource2")
	@RequestMapping(value="saveTwo",method= RequestMethod.POST)
	public ServiceResponse savetwo(@RequestBody Hello hello){
		hello.setCreateTime(Timestamp.valueOf(new Timestamp(System.currentTimeMillis()).toString()));
		helloimpl.savetwo(hello);
		return ServiceResponse.getInstance("0", "ok");
	}
	
	
	/**
	 * 不指定 @DataSource  走默认数据源
	 * @param hello
	 * @return
	 */
	@RequestMapping(value="savedef",method= RequestMethod.POST)
	public ServiceResponse savedef(@RequestBody Hello hello){
		
		hello.setCreateTime(Timestamp.valueOf(hello.getCreatedate()));
		helloimpl.save(hello);
		
		return ServiceResponse.getInstance("0", "ok");
	}
	
	@DataSource(value="dataSource2")
	@RequestMapping(value="fetchAll",method= RequestMethod.POST)
	public ServiceResponse fetchAll(){
		
		List<Hello> hellos = helloimpl.fetchAll(new Hello());
		for(Hello h : hellos){
			h.setCreatedate(h.getCreateTime().toString());
		}
		return ServiceResponse.getInstance("0", hellos);
	}
	
	
	@RequestMapping(value="update",method= RequestMethod.POST)
	public ServiceResponse update(){//@RequestBody Hello hello
		
		//hello.setCreateTime(Timestamp.valueOf(hello.getCreatedate()));
		Hello hello = new Hello();
		hello.setId("abfc5e70609911e7b06c00ffa6891afb");
		hello.setName("1234");
		helloimpl.update(hello);
		
		return ServiceResponse.getInstance("0", "ok");
	}
	
	@RequestMapping(value="del",method= RequestMethod.POST)
	public ServiceResponse del(){//@RequestBody Hello hello
		
		//hello.setCreateTime(Timestamp.valueOf(hello.getCreatedate()));
		Hello hello = new Hello();
		hello.setId("abfc5e70609911e7b06c00ffa6891afb");
		hello.setName("1234");
		helloimpl.del(hello);
		
		return ServiceResponse.getInstance("0", "ok");
	}
	
	
	@RequestMapping(value="list",method= RequestMethod.GET)//
	public String  list(@RequestParam(value="name",required = false) String name,ModelMap model){
		//logger.info(JsonUtil.objectToJson(hello));
		//hello.setCreateTime(Timestamp.valueOf(hello.getCreatedate()));
		List<Hello> hellos = helloimpl.fetchAll(new Hello());
		
		model.addAttribute("test", JsonUtil.objectToJson(hellos));
		model.addAttribute("lists", hellos);
//		 ModelAndView mv = new ModelAndView("list");
//		 mv.addObject("test", JsonUtil.objectToJson(hellos));
//		 mv.addObject("lists", hellos);
		return "list";
	}
	
	
	
		@RequestMapping(value="downloadAttachment",method= RequestMethod.GET)
		public void downloadAttachment(@RequestParam(value="attachmentName") String filename,HttpServletRequest request,HttpServletResponse response){
				
			try {
				String str = PublicUtil.checkfileISExist(filename,request);
				if(str != null){
					//return ServiceResponse.getInstance(Constant.QT_0.getCode(), str);	
				}
				
				PublicUtil.download(filename, request,response);
				
				
				//return ServiceResponse.getInstance(Constant.QT_0.getCode(), new ArrayList());	
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				//return ServiceResponse.getInstance(Constant.QT_9999.getCode(), e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//return null;
					
			
		}
	
}
