package com.man.erpcenter.sales.biz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.man.erpcenter.sales.biz.mapper.MsgInfoPoMapper;
import com.man.erpcenter.sales.client.po.MsgInfoPo;
import com.man.erpcenter.sales.client.service.MsgInfoService;

public class MsgInfoServiceImpl implements MsgInfoService {

	@Autowired
	private MsgInfoPoMapper msgInfoMapper;

	@Override
	public List<MsgInfoPo> selectList(int num) {
		return msgInfoMapper.selectList(num);
	}

}
