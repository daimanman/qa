package com.man.erpcenter.sales.biz.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface QemotCommentReplyMapper {
   
	public int insertBatchCommentReply(Map params);
	
	public int insertBatchCommentReplyByList(@Param("replyList") List replyList);
}