package com.man.erpcenter.sales.biz.manager;

import java.util.List;

public class StartUserThread implements Runnable {

	public QqInfoManager infoManager;
	
	public List uids;
	
	public int flag = 0;
	
	public long startId;
	
	public long endId;
	
	@Override
	public void run() {
		if(infoManager != null){
			if(flag == 0){
				infoManager.startScratch();
			}
			
			if(flag == 1){
				infoManager.getSpecUids(uids);
			}
			
			if(flag == 2){
				infoManager.retryCrawMsgWithId(startId, endId);
			}
		}
	}

}
