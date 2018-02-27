package com.man.erpcenter.sales.client.service;

import java.util.Map;

import com.man.erpcenter.common.pageinfo.PageResult;
import com.man.erpcenter.common.pageinfo.QueryParams;

public interface QphotoInfoService {

	/**
	 * 查询用户列表
	 * 
	 * @param queryItems
	 * @return
	 */
	public PageResult<Map<String, Object>> queryPhotoList(QueryParams queryParams);

}
