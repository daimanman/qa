package com.man.erpcenter.sales.biz.mq;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import com.dxm.mqservice.mq.BaseRocketMqConsumer;
import com.man.erpcenter.sales.biz.manager.QqInfoManager;
import com.man.erpcenter.sales.biz.util.ConstaintsUtil;
import com.man.erpcenter.sales.client.constant.MqMsgInfoEnum;
import com.man.erpcenter.sales.client.mqvo.UserInfoVo;

public class MqQuserInfoConsumerN extends BaseRocketMqConsumer {

	
	@Autowired
	private QqInfoManager qqInfoManager;

	@Override
	public String getTopic() {
		return MqMsgInfoEnum.ADD_USER_INFO_N.topic;
	}

	@Override
	public String getTags() {
		return MqMsgInfoEnum.ADD_USER_INFO_N.tags;
	}

	@Override
	public boolean doConsumeMessage(Serializable message) {
		if (ConstaintsUtil.FLAG_MQ.equals(getDisabled())) {
			return true;
		}
		try {
			if (null != message && message instanceof UserInfoVo) {
				UserInfoVo vo = (UserInfoVo) message;
				qqInfoManager.addUidN(vo.uid);
			}
		} finally {
			return true;
		}
	}

}
