package com.man.erpcenter.sales.biz.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.man.erpcenter.common.utils.ObjectUtil;
import com.man.erpcenter.elasticsearch.service.ElasticSearchService;
import com.man.erpcenter.sales.biz.job.EsImportDataThread;
import com.man.erpcenter.sales.biz.mapper.QuserInfoPoMapper;

public class ElasticSearchManager {

	private Logger logger  = LoggerFactory.getLogger(ElasticSearchManager.class);
	
	@Autowired
	private ElasticSearchService elasticSearchService;
	
	@Autowired
	private QuserInfoPoMapper infoMapper;
	

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
	
	
	public void importMySqlData(String tableName){
		int pageSize = 20000;
		int page = 1;
		int start = (page-1)*pageSize;
		Map<String,Object> bizParams = new HashMap<String,Object>();
		bizParams.put("tableName",tableName);
		bizParams.put("start",start);
		bizParams.put("pageSize",pageSize);
		List<Map<String,Object>> datas = infoMapper.queryList(bizParams);
		int total = infoMapper.queryListCount(bizParams);
		int totalPage = (total+pageSize-1)/pageSize;
		
		new Thread(new EsImportDataThread(elasticSearchService, ElasticSearchService.QQ_INDEX,tableName, datas)).start();
		
		page++;
		for(;page <=totalPage;page++){
			start = (page-1)*pageSize;
			bizParams.put("start",start);
			List<Map<String,Object>> nextDatas = infoMapper.queryList(bizParams);
			new Thread(new EsImportDataThread(elasticSearchService, ElasticSearchService.QQ_INDEX,tableName, nextDatas)).start();
		}
	}
	public void importMySqlData01(String tableName){
		int pageSize = 30000;
		int page = 1;
		int start = (page-1)*pageSize;
		Map<String,Object> bizParams = new HashMap<String,Object>();
		bizParams.put("tableName",tableName);
		bizParams.put("start",start);
		bizParams.put("pageSize",pageSize);
		List<Map<String,Object>> datas = infoMapper.queryList(bizParams);
		
		
		new Thread(new EsImportDataThread(elasticSearchService, ElasticSearchService.QQ_INDEX,tableName, datas)).start();
		
		page++;
		for(;page >0;page++){
			logger.info(tableName+"-----page----"+page);
			start = (page-1)*pageSize;
			bizParams.put("start",start);
			List<Map<String,Object>> nextDatas = infoMapper.queryList(bizParams);
			if(nextDatas == null || nextDatas.size() == 0){
				logger.info(tableName+" totalPage:="+page);
				break;
			}else{
				new Thread(new EsImportDataThread(elasticSearchService, ElasticSearchService.QQ_INDEX,tableName, nextDatas)).start();
			}
			
		}
	}
	
	public void importMySqlData02(String tableName){
		int pageSize = 30000;
		Map<String,Object> queryParams = new HashMap<String,Object>();
		queryParams.put("tableName", tableName);
		Map<String,Object> idMap = infoMapper.getMaxMinId(queryParams);
		long minId = ObjectUtil.parseLong(idMap.get("minId"));
		long maxId = ObjectUtil.parseLong(idMap.get("maxId"));
		String indexName = tableName+"_idx";
		
		long startId = minId;
		for(;startId <= maxId;){
			queryParams.put("startId",startId);
			long endId = startId+30000-1;
			queryParams.put("endId", endId);
			logger.info(tableName+" get data from "+startId+" to "+endId);
			List<Map<String,Object>> datas  = infoMapper.queryWithIdRange(queryParams);
			if(datas == null || datas.size() == 0){
				break;
			}else{
				new Thread(new EsImportDataThread(elasticSearchService,indexName,tableName, datas)).start();
			}
			startId = endId+1;
			
//			try {
//				Thread.sleep(3000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			
		}
	}

}
