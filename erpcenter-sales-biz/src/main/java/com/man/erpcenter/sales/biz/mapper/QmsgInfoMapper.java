package com.man.erpcenter.sales.biz.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.man.erpcenter.sales.client.mqvo.QemotInfoMqVo;

public interface QmsgInfoMapper {

	public int insertMsgInfo(Map<String,Object> msgInfoMap);
	
	public int insertMsgInfoBatch(@Param("msgInfoList") List msgInfoList);
	
	public int updateMsgTotalNum(QemotInfoMqVo qemotInfoMqVo );
	
	public long getMaxId();
}
