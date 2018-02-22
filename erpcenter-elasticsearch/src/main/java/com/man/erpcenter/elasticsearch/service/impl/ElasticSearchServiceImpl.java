package com.man.erpcenter.elasticsearch.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.man.erpcenter.common.basequery.QueryItem;
import com.man.erpcenter.common.pageinfo.QueryParams;
import com.man.erpcenter.common.pageinfo.PageResult;
import com.man.erpcenter.common.pageinfo.SortParams;
import com.man.erpcenter.common.utils.IDGenerator;
import com.man.erpcenter.common.utils.ObjectUtil;
import com.man.erpcenter.elasticsearch.basequery.QueryBuilderParser;
import com.man.erpcenter.elasticsearch.query.Criterion;
import com.man.erpcenter.elasticsearch.service.ElasticSearchService;

public class ElasticSearchServiceImpl implements ElasticSearchService {

	private Logger logger  = LoggerFactory.getLogger(ElasticSearchServiceImpl.class);
	
	@Autowired
	private TransportClient client;

	public TransportClient getClient() {
		return client;
	}

	public void setClient(TransportClient client) {
		this.client = client;
	}

	/**
	 * 索引文档
	 *
	 * @param index
	 *            索引
	 * @param type
	 *            类型
	 * @param doc
	 *            数据
	 * @param refresh
	 *            是否刷新
	 */
	@Override
	public Map<String, Object> index(String index, String type, Map<String, Object> doc, boolean refresh) {
		String id = (String) (doc.get(ID_NAME) != null ? doc.get(ID_NAME) : UUID.randomUUID().toString());
		doc.put("uid", id);
		IndexRequestBuilder indexRequest = client.prepareIndex(index, type, id).setSource(doc);
		if (refresh) {
			indexRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
		}
		indexRequest.get();
		return doc;
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
	@Override
	public Map<String, Object> update(String index, String type, String id, Map<String, Object> doc, boolean refresh) {
		UpdateRequestBuilder updateRequest = client.prepareUpdate(index, type, id).setDoc(doc);
		if (refresh) {
			updateRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
		}
		updateRequest.get();
		return doc;
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
	@Override
	public Map<String, Object> get(String index, String type, String id) {
		GetRequestBuilder getRequest = client.prepareGet(index, type, id);
		return getRequest.get().getSource();
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
	@Override
	public void delete(String index, String type, String id, boolean refresh) {
		DeleteRequestBuilder deleteRequest = client.prepareDelete(index, type, id);
		if (refresh) {
			deleteRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
		}
		deleteRequest.get();
	}

	/**
	 * 索引文档
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
	@Override
	public List<Map<String, Object>> multiIndex(String index, String type, List<Map<String, Object>> docList,
			boolean refresh) {

		if (null != docList && docList.size() > 0) {
			BulkRequestBuilder bulkRequest = client.prepareBulk();
			for (Map<String, Object> doc : docList) {
				String innerId = ObjectUtil.toString(doc.get("id"));
				bulkRequest.add(client.prepareIndex(index, type, innerId).setSource(doc));
			}
			if (refresh) {
				bulkRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
			}
			bulkRequest.get();
		}

		return docList;
	}

	@Override
	public List<Map<String, Object>> multiIndex(String index, String type, List<Map<String, Object>> docList,
			boolean refresh, String idProp) {

		if (null != docList && docList.size() > 0) {
			BulkRequestBuilder bulkRequest = client.prepareBulk();
			for (Map<String, Object> doc : docList) {
				String innerId = IDGenerator.uuid();
				doc.put(ID_NAME, innerId);
				bulkRequest.add(client.prepareIndex(index, type, innerId).setSource(doc));
			}
			if (refresh) {
				bulkRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
			}
			bulkRequest.get();
		}

		return docList;
	}

	/**
	 * 批量更新文档
	 *
	 * @param index
	 *            索引
	 * @param type
	 *            类型
	 * @param docMap
	 *            文档map
	 * @param refresh
	 *            是否刷新
	 */
	@Override
	public Map<String, Map<String, Object>> multiUpdate(String index, String type,
			Map<String, Map<String, Object>> docMap, boolean refresh) {
		// BulkRequestBuilder bulkRequest = client.prepareBulk();
		// docMap.forEach((key, value) -> {
		// value.put(ID_NAME, key);
		// bulkRequest.add(client.prepareUpdate(index, type,
		// key).setDoc(value));
		// });
		// if (refresh) {
		// bulkRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
		// }
		// LOGGER.info(bulkRequest.toString());
		// bulkRequest.get();
		return docMap;
	}

	/**
	 * 批量获取文档
	 *
	 * @param index
	 *            索引
	 * @param type
	 *            类型
	 * @param ids
	 *            主键数组
	 * @return 文档
	 */
	@Override
	public Map<String, Map<String, Object>> multiGet(String index, String type, String[] ids) {
		MultiGetRequestBuilder multiGetRequest = client.prepareMultiGet().add(index, type, ids);
		Map<String, Map<String, Object>> docMap = new HashMap<String, Map<String, Object>>();
		for (MultiGetItemResponse itemResponse : multiGetRequest.get()) {
			GetResponse response = itemResponse.getResponse();
			if (response.isExists()) {
				docMap.put(response.getId(), response.getSource());
			}
		}
		return docMap;
	}

	/**
	 * 批量删除文档
	 *
	 * @param index
	 *            索引
	 * @param type
	 *            类型
	 * @param ids
	 *            主键数组
	 * @param refresh
	 *            是否刷新
	 */
	@Override
	public void multiDelete(String index, String type, String[] ids, boolean refresh) {
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		// Arrays.stream(ids).forEach(id ->
		// bulkRequest.add(client.prepareDelete(index, type, id)));
		// if (refresh) {
		// bulkRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
		// }
		// LOGGER.info(bulkRequest.toString());
		bulkRequest.get();
	}

	/**
	 * 设置元数据
	 *
	 * @param index
	 *            索引名
	 * @param type
	 *            类型名
	 * @param metadataStr
	 *            元数据字符串
	 * @return 是否成功标识
	 */
	@Override
	public boolean setMetadata(String index, String type, String metadataStr) {
		// Map<String, String> properties = new HashMap<>();
		// JSONArray jsonArray =
		// JSONObject.parseObject(JSONObject.parseObject(metadataStr).getString("data")).
		// getJSONArray("children").getJSONObject(1).getJSONArray("children");
		// for (Object object : jsonArray) {
		// JSONObject jsonObject = (JSONObject) object;
		// String key = jsonObject.getString("uikey");
		// String label = jsonObject.getString("uititle");
		// properties.put(key, label);
		// }
		// JSONObject metadata = new JSONObject();
		// metadata.put("properties", properties);
		// metadata.put("source", metadataStr);
		//
		// JSONObject mapping = new JSONObject();
		// mapping.put("_meta", metadata);
		//
		// IndicesAdminClient adminClient = client.admin().indices();
		// if (!adminClient.prepareExists(index).get().isExists()) {
		// adminClient.prepareCreate(index).execute();
		// }
		// PutMappingRequestBuilder requestBuilder =
		// adminClient.preparePutMapping(new String[]{index}).setType(type);
		// requestBuilder.setSource(mapping.toJSONString(), XContentType.JSON);
		// return requestBuilder.execute().actionGet().isAcknowledged();
		return false;
	}

	@Override
	public Map<String, Object> filterForObject(String index, String type, Criterion criterion) {

		return null;
	}

	@Override
	public long count(String[] indexes, String[] types, Criterion criterion) {
		return 0;
	}

	/**
	 * 获取文档内容
	 *
	 * @param response
	 *            {@link SearchResponse}
	 * @return 文档列表
	 */
	private List<Map<String, Object>> getDocContent(SearchResponse response) {
		List<Map<String, Object>> docList = new ArrayList<>();
		for (SearchHit hit : response.getHits()) {
			Map<String, Object> doc = hit.getSource();
			doc.put("_index", hit.getIndex());
			doc.put("_type", hit.getType());
			docList.add(doc);
		}
		return docList;
	}

	private void setSort(SearchRequestBuilder searchRequest, List<SortParams> sorts) {
		
		//TODO 需要判断字段是否支持排序,全文检索的字段不能参与排序处理
		
		// 是否需要排序
		if (null != sorts && sorts.size() > 0) {
			// 设置排序字段
			for (SortParams sort : sorts) {
				if (sort.getField() != null && !sort.getField().trim().equals("") && sort.getSort() != null
						&& (sort.getSort().toLowerCase().equals("asc")
								|| sort.getSort().toLowerCase().equals("desc"))) {
					searchRequest
							.addSort(new FieldSortBuilder(sort.getField()).order(SortOrder.valueOf(sort.getSort())));
				}
			}
		}
	}

	/**
	 * 查询文档列表
	 * 
	 * @param index
	 * @param type
	 * @param size
	 * @param queryParams
	 * @return
	 */
	@Override
	public List<Map<String, Object>> filterList(String index, String type, int size, List<QueryItem> queryParams,
			List<SortParams> sorts) {
		SearchRequestBuilder searchRequest = client.prepareSearch(index).setSize(size).setTypes(type)
				.setPostFilter(new QueryBuilderParser().parseQueryItems(queryParams));
		setSort(searchRequest, sorts);
		return getDocContent(searchRequest.get());
	}

	/**
	 * 分页获取数据
	 */
	@Override
	public PageResult<Map<String, Object>> filterPage(String index, String type, QueryParams queryParams
			) {
		PageResult<Map<String, Object>> pageResult = new PageResult<Map<String, Object>>();
		SearchRequestBuilder searchRequest = client.prepareSearch(index).setTypes(type)
				.setPostFilter(new QueryBuilderParser().parseQueryItems(queryParams.getQueryItems()));
		searchRequest.setFrom(queryParams.getOffset()).setSize(queryParams.getPageSize());
		setSort(searchRequest, queryParams.getSorts());
		SearchResponse searchResponse = searchRequest.get();
		pageResult.setTotal(searchResponse.getHits().getTotalHits());
		pageResult.setPage(queryParams.getPage());
		pageResult.setPageSize(queryParams.getPageSize());
		pageResult.setDatas(getDocContent(searchResponse));
		return pageResult;
	}

	@Override
	public Map<String, Object> filterOneObj(String index, String type, QueryItem queryItem) {
		List<QueryItem> items = new ArrayList<QueryItem>();
		items.add(queryItem);
		List<Map<String,Object>> datas = filterList(index, type, 1, items, null);
		if(datas != null && datas.size() > 0){
			return datas.get(0);
		}
		return null;
	}

}
