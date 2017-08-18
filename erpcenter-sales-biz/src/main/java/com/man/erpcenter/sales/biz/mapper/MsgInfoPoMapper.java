package com.man.erpcenter.sales.biz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.man.erpcenter.sales.client.po.MsgInfoPo;

public interface MsgInfoPoMapper {

	List<MsgInfoPo> selectList(@Param("num") int num);
	
	int  insert(MsgInfoPo msgInfo);
}
