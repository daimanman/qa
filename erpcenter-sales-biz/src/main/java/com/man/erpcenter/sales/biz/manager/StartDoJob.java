package com.man.erpcenter.sales.biz.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class StartDoJob implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private QqInfoManager qqInfoManager;
	
	public static int START_FLAG = 0;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getParent() == null){
			//TODO 任务
			//qqInfoManager.startScratch();
			//qqInfoManager.startScratchMinus();
			//qqInfoManager.retryCrawMsg();
			System.out.println(" ##########start###########");
		}
	}

}
