package com.man.erpcenter.sales.client.service;

import java.util.Map;

import com.man.erpcenter.common.pageinfo.PageResult;
import com.man.erpcenter.common.pageinfo.QueryParams;

public interface QemotInfoService {

	/**
	 * 查询用户列表
	 * @param queryItems
	 * @return
	 */
	public PageResult<Map<String,Object>> queryEmotList(QueryParams queryParams);
	
	/**
	 * 根据uid查询单个对象信息
	 * @param uid
	 * @return
	 */
	public Map<String,Object> getEmotInfo(String uid);
	
	
	
	
}
