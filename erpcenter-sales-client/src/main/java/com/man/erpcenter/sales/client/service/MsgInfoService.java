package com.man.erpcenter.sales.client.service;

import java.util.List;

import com.man.erpcenter.sales.client.po.MsgInfoPo;

public interface MsgInfoService {

	List<MsgInfoPo> selectList(int num);
}
