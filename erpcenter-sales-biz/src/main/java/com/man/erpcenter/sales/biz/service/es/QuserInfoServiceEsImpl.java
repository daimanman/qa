package com.man.erpcenter.sales.biz.service.es;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.man.erpcenter.common.basequery.QueryItem;
import com.man.erpcenter.common.basequery.QueryTypeEnum;
import com.man.erpcenter.common.constants.IdxConstant;
import com.man.erpcenter.common.pageinfo.PageResult;
import com.man.erpcenter.common.pageinfo.QueryParams;
import com.man.erpcenter.elasticsearch.service.ElasticSearchService;
import com.man.erpcenter.sales.client.service.QuserInfoService;

public class QuserInfoServiceEsImpl implements QuserInfoService {

	@Autowired
	private ElasticSearchService elasticSearchService;
	
	@Override
	public PageResult<Map<String, Object>> queryUserList(QueryParams queryParams) {
		return elasticSearchService.filterPage(IdxConstant.QUSER_INFO_IDX, IdxConstant.QUSER_INFO_TYPE, queryParams);
	}

	@Override
	public Map<String, Object> getUserInfo(String uid) {
		QueryItem item = new QueryItem();
		item.field = "uid";
		item.value = uid;
		item.matchType = QueryTypeEnum.IN.getType();
		return elasticSearchService.filterOneObj(IdxConstant.QUSER_INFO_IDX, IdxConstant.QUSER_INFO_TYPE, item);
	}

}
