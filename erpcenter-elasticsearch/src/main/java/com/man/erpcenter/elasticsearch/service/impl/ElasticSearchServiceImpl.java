package com.man.erpcenter.elasticsearch.service.impl;

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
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;

import com.man.erpcenter.common.utils.IDGenerator;
import com.man.erpcenter.common.utils.IdWorker;
import com.man.erpcenter.common.utils.ObjectUtil;
import com.man.erpcenter.elasticsearch.service.ElasticSearchService;

public class ElasticSearchServiceImpl implements ElasticSearchService {

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

}
