package com.man.erpcenter.sales.biz.mq;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dxm.mqservice.mq.BaseRocketMqConsumer;
import com.dxm.mqservice.mq.MsgSenderService;
import com.man.erpcenter.sales.biz.mapper.MsgInfoPoMapper;
import com.man.erpcenter.sales.biz.util.ConstaintsUtil;
import com.man.erpcenter.sales.client.constant.MqMsgInfoEnum;
import com.man.erpcenter.sales.client.mqvo.QemotInfoMqVo;
import com.man.erpcenter.sales.client.po.MsgInfoPo;

public class MqMsgInfoConsumer extends BaseRocketMqConsumer {

	@Autowired
	private MsgInfoPoMapper msgInfoPoMapper;
	

	@Override
	public String getTopic() {
		return MqMsgInfoEnum.ADD_MSG_INFO.topic;
	}

	@Override
	public String getTags() {
		return MqMsgInfoEnum.ADD_MSG_INFO.tags;
	}

	@Override
	public boolean doConsumeMessage(Serializable message) {
		if(ConstaintsUtil.FLAG_MQ.equals(getDisabled())){
			return true;
		}
		if (null != message && message instanceof MsgInfoPo) {
			MsgInfoPo po = (MsgInfoPo) message;
			msgInfoPoMapper.insert(po);
			return true;
		}
		return true;
	}

}
