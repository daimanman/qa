package com.man.erpcenter.sales.biz.manager;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.man.erpcenter.elasticsearch.service.ElasticSearchService;

public class ElasticSearchManager {

	@Autowired
	private ElasticSearchService elasticSearchService;

	public ElasticSearchService getElasticSearchService() {
		return elasticSearchService;
	}

	public void setElasticSearchService(ElasticSearchService elasticSearchService) {
		this.elasticSearchService = elasticSearchService;
	}

	/**
	 * 索引文档
	 *
	 * @param index
	 *            索引
	 * @param type
	 *            类型
	 * @param doc
	 *            文档
	 * @param refresh
	 *            是否刷新
	 */
	public Map<String, Object> index(String index, String type, Map<String, Object> doc, boolean refresh) {
		return elasticSearchService.index(index, type, doc, refresh);
	}

	/**
	 * 更新文档
	 *
	 * @param index
	 *            索引
	 * @param type
	 *            类型
	 * @param id
	 *            主键
	 * @param doc
	 *            文档
	 * @param refresh
	 *            是否刷新
	 */
	public Map<String, Object> update(String index, String type, String id, Map<String, Object> doc, boolean refresh) {
		return elasticSearchService.update(index, type, id, doc, refresh);
	}

	/**
	 * 获取文档
	 *
	 * @param index
	 *            索引
	 * @param type
	 *            类型
	 * @param id
	 *            主键
	 * @return 文档
	 */
	public Map<String, Object> get(String index, String type, String id) {
		return elasticSearchService.get(index, type, id);
	}

	/**
	 * 删除文档
	 *
	 * @param index
	 *            索引
	 * @param type
	 *            类型
	 * @param id
	 *            主键
	 * @param refresh
	 *            是否刷新
	 */
	public void delete(String index, String type, String id, boolean refresh) {
		elasticSearchService.delete(index, type, id, refresh);
	}

	/**
	 * 批量索引文档
	 *
	 * @param index
	 *            索引
	 * @param type
	 *            类型
	 * @param docList
	 *            文档列表
	 * @param refresh
	 *            是否刷新
	 */
	public List<Map<String, Object>> multiIndex(String index, String type, List<Map<String, Object>> docList,
			boolean refresh) {
		return elasticSearchService.multiIndex(index, type, docList, refresh);
	}

}
