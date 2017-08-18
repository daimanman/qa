package com.man.erpcenter.sales.biz.mq;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import com.dxm.mqservice.mq.BaseRocketMqConsumer;
import com.man.erpcenter.sales.biz.manager.QqInfoManager;
import com.man.erpcenter.sales.biz.util.ConstaintsUtil;
import com.man.erpcenter.sales.client.constant.MqMsgInfoEnum;
import com.man.erpcenter.sales.client.mqvo.QemotInfoMqVo;

public class MqQqMsgDBConsumer extends BaseRocketMqConsumer {

	@Autowired
	private QqInfoManager qqInfoManager;

	@Override
	public String getTopic() {
		return MqMsgInfoEnum.ADD_MSG_DB.topic;
	}

	@Override
	public String getTags() {
		return MqMsgInfoEnum.ADD_MSG_DB.tags;
	}

	@Override
	public boolean doConsumeMessage(Serializable message) {
		if (ConstaintsUtil.FLAG_MQ.equals(getDisabled())) {
			return true;
		}
		if (null != message && message instanceof QemotInfoMqVo) {
			 QemotInfoMqVo infoMqVo = (QemotInfoMqVo) message;
			try {
				//qqInfoManager.insertMsgIntoDB(infoMqVo);
				qqInfoManager.insertMsgIntoDBBatch(infoMqVo);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return true;
	}

}
