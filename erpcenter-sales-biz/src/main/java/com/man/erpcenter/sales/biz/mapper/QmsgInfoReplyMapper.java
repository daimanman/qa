package com.man.erpcenter.sales.biz.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface QmsgInfoReplyMapper {

	public int insertBatchReplys(Map<String,Object> msgInfoMap);
	
	
	public int insertBatchReplysByList(@Param("replyList")List replyList);
}
