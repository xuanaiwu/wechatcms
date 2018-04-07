package com.dayuan.task;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.dayuan.bean.BusLoanInfo;
import com.dayuan.service.BusLoanInfoService;

public class TestJob extends QuartzJobBean{
	 private Logger log = Logger.getLogger(TestJob.class);
	 
	// Service start 商贷主表
	//@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	@Resource
	private BusLoanInfoService<BusLoanInfo> busLoanInfoService;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		log.info("#################start#############"+new Date());
		
		try {
			BusLoanInfo BusLoanInfo=new BusLoanInfo();
		//	busLoanInfoService.save(BusLoanInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("#################end#############"+new Date());
		
	}



}
