package com.man.erpcenter.sales.biz.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.man.erpcenter.sales.client.mqvo.QemotInfoMqVo;

public interface QemotInfoMapper {
    
	public  int  insertQemotInfo(Map qemotInfoparams);
	
	public  int  insertQemotInfoBatch(@Param("qemotInfoList") List qemotInfoList);
	
	public int updateEmotTotalNum(QemotInfoMqVo vo);
	
	public long getMaxId();
	
}