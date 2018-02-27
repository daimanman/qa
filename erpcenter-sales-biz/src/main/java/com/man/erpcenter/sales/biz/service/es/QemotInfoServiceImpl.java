package com.man.erpcenter.sales.biz.service.es;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.man.erpcenter.common.constants.IdxConstant;
import com.man.erpcenter.common.pageinfo.PageResult;
import com.man.erpcenter.common.pageinfo.QueryParams;
import com.man.erpcenter.elasticsearch.service.ElasticSearchService;
import com.man.erpcenter.sales.client.service.QemotInfoService;

public class QemotInfoServiceImpl implements QemotInfoService {

	@Autowired
	private ElasticSearchService elasticSearchService;

	@Override
	public PageResult<Map<String, Object>> queryEmotList(QueryParams queryParams) {
		return elasticSearchService.filterPage(IdxConstant.QEMOT_INFO_IDX, IdxConstant.QEMOT_INFO_TYPE, queryParams);
	}

	@Override
	public Map<String, Object> getEmotInfo(String uid) {
		// TODO Auto-generated method stub
		return null;
	}

}
