package com.man.erpcenter.sales.biz.job;

import com.man.erpcenter.sales.biz.manager.ElasticSearchManager;

public class ImportTableThread implements Runnable {

	private ElasticSearchManager elasticSearchManager;

	private String tableName;

	public ImportTableThread(ElasticSearchManager elasticSearchManager, String tableName) {
		super();
		this.elasticSearchManager = elasticSearchManager;
		this.tableName = tableName;
	}

	@Override
	public void run() {
		elasticSearchManager.importMySqlData02(tableName);
	}

}
