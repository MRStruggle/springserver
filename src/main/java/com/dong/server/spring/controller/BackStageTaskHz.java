package com.dong.server.spring.controller;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.dong.server.api.ServiceResponse;
import com.dong.server.spring.entity.Hello;
import com.dong.server.spring.service.IHello;


@Component
public class BackStageTaskHz {

	private Logger logger = Logger.getLogger(BackStageTaskHz.class);
	
	
	@Resource
	private IHello helloimpl;
	
	
	
	/**
	 *  
		名称	类型	单位	说明
		cron	String	-	cron表达式
		zone	String	-	时区字符串（一般不需要设置）
		fixedDelay	long	毫秒	调度间隔，下一个任务开始时间与上一个任务结束时间间隔[F-S]
		fixedDelayString	String	毫秒	调度间隔，下一个任务开始时间与上一个任务结束时间间隔，字符串表示[F-S]
		fixedRate	long	毫秒	调度间隔，下一个任务开始时间与上一个任务开始时间间隔[S-S]
		fixedRateString	String	毫秒	调度间隔，下一个任务开始时间与上一个任务开始时间间隔，字符串表示[S-S]
		initialDelay	long	毫秒	调度器启动延迟时间
		initialDelayString	String	毫秒	调度器启动延迟时间，字符串表示
	 * InstructionTask.java 方法的描述
	 * @author 笨东东
	 * @Since 2017年6月28日 下午12:00:41
	 */
	@Scheduled(fixedRate = 1000 * 10,initialDelay = 1000 * 5) 
	@DataSource(value="dataSource2")
    public void taskRun(){ 
		//do something
        logger.info("InstructionTask run ..."+helloimpl); 
        Hello hello = new Hello();
        hello.setCreateTime(Timestamp.valueOf(new Timestamp(System.currentTimeMillis()).toString()));
		helloimpl.savetwo(hello);
		
    }
}
