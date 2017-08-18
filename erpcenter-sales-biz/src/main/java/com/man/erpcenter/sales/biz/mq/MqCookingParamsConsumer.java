package com.man.erpcenter.sales.biz.mq;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dxm.mqservice.mq.BaseRocketMqConsumer;
import com.man.erpcenter.sales.biz.manager.QqInfoManager;
import com.man.erpcenter.sales.biz.manager.StartDoJob;
import com.man.erpcenter.sales.biz.util.ConstaintsUtil;
import com.man.erpcenter.sales.client.constant.MqMsgInfoEnum;
import com.man.erpcenter.sales.client.mqvo.QinfoCookieMqVo;

public class MqCookingParamsConsumer extends BaseRocketMqConsumer {

	@Autowired
	private QqInfoManager qqInfoManager;
	
	private ConcurrentHashMap<Integer,Integer> initLock = new ConcurrentHashMap<Integer,Integer>();
	
	@Override
	public String getTopic() {
		return MqMsgInfoEnum.Q_CONFIG_COOKIE_PARAM.topic;
	}

	@Override
	public String getTags() {
		return MqMsgInfoEnum.Q_CONFIG_COOKIE_PARAM.tags;
	}

	@SuppressWarnings("finally")
	@Override
	public boolean doConsumeMessage(Serializable message) {
		System.out.println("--------"+getDisabled());
		if(ConstaintsUtil.FLAG_MQ.equals(getDisabled())){
			return true;
		}
		if(message instanceof QinfoCookieMqVo ){
		QinfoCookieMqVo  qinfoCookieMqVo = (QinfoCookieMqVo)message;
		try{
			synchronized (initLock) {
				if(qqInfoManager.uids.size() == 0){
					System.out.println(JSON.toJSONString(qinfoCookieMqVo));
					qqInfoManager.initQParamCookieMapUids(qinfoCookieMqVo.uids, qinfoCookieMqVo.paramsMap, qinfoCookieMqVo.cookiesMap);
				}
			}
			//qqInfoManager.startScratch();
			StartDoJob.START_FLAG = 1;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return true;
		}	
		}
		return true;
	}

}
