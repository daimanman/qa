package com.man.erpcenter.elasticsearch.service;

import java.util.List;
import java.util.Map;

public interface ElasticSearchService {
	String ID_NAME = "uuid"; // 主键属性名
	
	String QQ_INDEX = "qq_alias";
	String QQ_USER_TYPE="qq_user";

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
	Map<String, Object> index(String index, String type, Map<String, Object> doc, boolean refresh);

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
	Map<String, Object> update(String index, String type, String id, Map<String, Object> doc, boolean refresh);

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
	Map<String, Object> get(String index, String type, String id);

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
	void delete(String index, String type, String id, boolean refresh);

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
	List<Map<String, Object>> multiIndex(String index, String type, List<Map<String, Object>> docList, boolean refresh);
	
	List<Map<String, Object>> multiIndex(String index, String type, List<Map<String, Object>> docList, boolean refresh,String idProp);

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
	Map<String, Map<String, Object>> multiUpdate(String index, String type, Map<String, Map<String, Object>> docMap,
			boolean refresh);

	/**
	 * 批量获取文档
	 *
	 * @param index
	 *            索引
	 * @param type
	 *            类型
	 * @param ids
	 *            主键数组
	 * @return 文档map
	 */
	Map<String, Map<String, Object>> multiGet(String index, String type, String[] ids);

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
	void multiDelete(String index, String type, String[] ids, boolean refresh);

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
	boolean setMetadata(String index, String type, String metadataStr);

}
