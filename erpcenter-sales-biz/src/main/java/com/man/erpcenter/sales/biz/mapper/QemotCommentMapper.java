package com.man.erpcenter.sales.biz.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface QemotCommentMapper {
    
	int insertQemotComment(Map params);
	
	int insertQemotCommentBatch(@Param("commentList") List qemotCommentList);
	
	public long getMaxId();
}