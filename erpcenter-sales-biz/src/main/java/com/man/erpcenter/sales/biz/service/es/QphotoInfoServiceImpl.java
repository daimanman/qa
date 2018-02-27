package com.man.erpcenter.sales.biz.service.es;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.man.erpcenter.common.constants.IdxConstant;
import com.man.erpcenter.common.pageinfo.PageResult;
import com.man.erpcenter.common.pageinfo.QueryParams;
import com.man.erpcenter.elasticsearch.service.ElasticSearchService;
import com.man.erpcenter.sales.client.service.QphotoInfoService;

public class QphotoInfoServiceImpl implements QphotoInfoService {
	@Autowired
	private ElasticSearchService elasticSearchService;

	@Override
	public PageResult<Map<String, Object>> queryPhotoList(QueryParams queryParams) {
		return elasticSearchService.filterPage(IdxConstant.QPHOTO_INFO_IDX, IdxConstant.QPHOTO_INFO_TYPE, queryParams);
	}

}
