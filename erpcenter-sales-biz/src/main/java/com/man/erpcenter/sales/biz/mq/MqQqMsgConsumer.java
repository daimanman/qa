package com.man.erpcenter.sales.biz.mq;

import java.io.Serializable;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.dxm.mqservice.mq.BaseRocketMqConsumer;
import com.man.erpcenter.sales.biz.manager.QqInfoManager;
import com.man.erpcenter.sales.biz.util.ConstaintsUtil;
import com.man.erpcenter.sales.client.constant.MqMsgInfoEnum;
import com.man.erpcenter.sales.client.mqvo.QemotInfoMqVo;

public class MqQqMsgConsumer extends BaseRocketMqConsumer {

	@Autowired
	private QqInfoManager qqInfoManager;
	
	@Override
	public String getTopic() {
		return MqMsgInfoEnum.ADD_MSG.topic;
	}

	@Override
	public String getTags() {
		return MqMsgInfoEnum.ADD_MSG.tags;
	}

	@SuppressWarnings("finally")
	@Override
	public boolean doConsumeMessage(Serializable message) {
		if(ConstaintsUtil.FLAG_MQ.equals(getDisabled())){
			return true;
		}
		if(null != message && message instanceof QemotInfoMqVo){
			QemotInfoMqVo qqInfoVo = (QemotInfoMqVo)message;
			try{
				Map<String,Object> resultMap =  qqInfoManager.startScratchMsgOne(qqInfoVo);
				if(null == resultMap){
					System.out.println("msgUids size = "+qqInfoManager.msgUids.size()+" uid = "+qqInfoVo.uid+" will be later reconsume");
					return false;
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				return true;
			}
		}
		return true;
	}

}
