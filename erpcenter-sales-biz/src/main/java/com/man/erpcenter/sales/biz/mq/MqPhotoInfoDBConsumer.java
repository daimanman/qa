package com.man.erpcenter.sales.biz.mq;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import com.dxm.mqservice.mq.BaseRocketMqConsumer;
import com.man.erpcenter.sales.biz.manager.QqInfoManager;
import com.man.erpcenter.sales.biz.util.ConstaintsUtil;
import com.man.erpcenter.sales.client.constant.MqMsgInfoEnum;
import com.man.erpcenter.sales.client.mqvo.QemotInfoMqVo;

public class MqPhotoInfoDBConsumer extends BaseRocketMqConsumer {

	@Autowired
	private QqInfoManager qqInfoManager;
	
	@Override
	public String getTopic() {
		
		return MqMsgInfoEnum.ADD_PHOTO_DB.topic;
	}

	@Override
	public String getTags() {
		return MqMsgInfoEnum.ADD_PHOTO_DB.tags;
	}

	@SuppressWarnings("finally")
	@Override
	public boolean doConsumeMessage(Serializable message) {
		if(ConstaintsUtil.FLAG_MQ.equals(getDisabled())){
			return true;
		}
		if(null != message && message instanceof QemotInfoMqVo){
			QemotInfoMqVo qemotInfoMqVo  = (QemotInfoMqVo)message;
			try{
			qqInfoManager.insertPhotoImgDB(qemotInfoMqVo);
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				return true;
			}
		}
		return true;
	}

}
