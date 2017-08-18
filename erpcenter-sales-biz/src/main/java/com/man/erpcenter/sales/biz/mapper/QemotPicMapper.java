package com.man.erpcenter.sales.biz.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface QemotPicMapper {
	
	public int insertBatchPics(Map qemotInfoparams);
	
	public int insertBatchPicsByList(@Param("picList")List picList);
}