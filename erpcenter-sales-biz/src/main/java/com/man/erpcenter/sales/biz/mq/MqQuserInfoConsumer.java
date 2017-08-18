package com.man.erpcenter.sales.biz.mq;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dxm.mqservice.mq.BaseRocketMqConsumer;
import com.dxm.mqservice.mq.MsgSenderService;
import com.man.erpcenter.sales.biz.manager.QqInfoManager;
import com.man.erpcenter.sales.biz.mapper.QuserInfoPoMapper;
import com.man.erpcenter.sales.biz.util.ConstaintsUtil;
import com.man.erpcenter.sales.client.constant.MqMsgInfoEnum;
import com.man.erpcenter.sales.client.mqvo.QemotInfoMqVo;
import com.man.erpcenter.sales.client.mqvo.UserInfoVo;

public class MqQuserInfoConsumer extends BaseRocketMqConsumer {

	@Autowired
	private QuserInfoPoMapper quserInfoPoMapper;

	@Autowired
	private MsgSenderService msgSenderService;
	
	@Autowired
	private QqInfoManager qqInfoManager;

	@Override
	public String getTopic() {
		return MqMsgInfoEnum.ADD_USER_INFO.topic;
	}

	@Override
	public String getTags() {
		return MqMsgInfoEnum.ADD_USER_INFO.tags;
	}

	@SuppressWarnings("finally")
	@Override
	public boolean doConsumeMessage(Serializable message) {
		if(ConstaintsUtil.FLAG_MQ.equals(getDisabled())){
			return true;
		}
		if (message != null && message instanceof UserInfoVo) {
			UserInfoVo userInfoVo = (UserInfoVo) message;
			try{
			qqInfoManager.addQuserInfo(userInfoVo);
			}finally{
				return true;
			}
		}
		return true;
	}

}
