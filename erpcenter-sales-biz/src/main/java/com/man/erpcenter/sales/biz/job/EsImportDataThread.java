package com.man.erpcenter.sales.biz.job;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.man.erpcenter.elasticsearch.service.ElasticSearchService;

public class EsImportDataThread implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(EsImportDataThread.class);
	
	private ElasticSearchService elasticSearchService;

	private String index;

	private String type;

	private List<Map<String, Object>> datas;

	public List<Map<String, Object>> getDatas() {
		return datas;
	}

	public void setDatas(List<Map<String, Object>> datas) {
		this.datas = datas;
	}

	public ElasticSearchService getElasticSearchService() {
		return elasticSearchService;
	}

	public void setElasticSearchService(ElasticSearchService elasticSearchService) {
		this.elasticSearchService = elasticSearchService;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public EsImportDataThread(ElasticSearchService elasticSearchService, String index, String type) {
		super();
		this.elasticSearchService = elasticSearchService;
		this.index = index;
		this.type = type;
	}
	
	

	public EsImportDataThread(ElasticSearchService elasticSearchService, String index, String type,
			List<Map<String, Object>> datas) {
		super();
		this.elasticSearchService = elasticSearchService;
		this.index = index;
		this.type = type;
		this.datas = datas;
	}

	@Override
	public void run() {
			if(null != elasticSearchService  && datas != null && datas.size() > 0){
				long startTime = System.currentTimeMillis();
				String threadName = Thread.currentThread().getName();
				//logger.error(threadName+" "+type+"starttime "+startTime+"");
				elasticSearchService.multiIndex(getIndex(), getType(), getDatas(), true);
				long endTime = System.currentTimeMillis();
				//logger.error(threadName+" "+type+" endtime "+endTime+"");
				logger.error(threadName+" spent time "+(endTime-startTime)+" ms ");
			}
	}

}
