package com.dong.server.spring.task;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 * InstructionTask.java
 * ==============================================
 * Copyright 2017-2017  by http://www.bhnhz.com
 * ----------------------------------------------
 * This is not a free software, without any authorization is not allowed to use and spread.
 * ==============================================
 * @desc :  TODO 类标题， 类功能描述
 * @author: shenjd（shenjd@51barh.com）
 * @version: v1.0.0
 * @since: 2017年6月28日 上午11:56:49
 */
@Component
public class BackStageTask {

private Logger logger = Logger.getLogger(BackStageTask.class);
	
	
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
//	@Scheduled(fixedRate = 1000 * 10,initialDelay = 1000 * 5)  
//    private void taskRun(){ 
//		//do something
//        logger.info("InstructionTask run ...");  
//    }
	
}
