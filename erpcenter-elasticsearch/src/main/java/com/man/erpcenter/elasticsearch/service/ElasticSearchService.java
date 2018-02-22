package com.man.erpcenter.elasticsearch.service;

import java.util.List;
import java.util.Map;

import com.man.erpcenter.common.basequery.QueryItem;
import com.man.erpcenter.common.pageinfo.QueryParams;
import com.man.erpcenter.common.pageinfo.PageResult;
import com.man.erpcenter.common.pageinfo.SortParams;
import com.man.erpcenter.elasticsearch.query.Criterion;

public interface ElasticSearchService {
	String ID_NAME = "uuid"; // 主键属性名

	String QQ_INDEX = "qq_alias";
	String QQ_USER_TYPE = "qq_user";

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

	List<Map<String, Object>> multiIndex(String index, String type, List<Map<String, Object>> docList, boolean refresh,
			String idProp);

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

	/**
	 * 过滤查找文档
	 *
	 * @param index
	 *            索引
	 * @param type
	 *            类型
	 * @param criterion
	 *            查询条件
	 * @return 文档
	 */
	Map<String, Object> filterForObject(String index, String type, Criterion criterion);

	/**
	 * 统计个数
	 *
	 * @param indexes
	 *            索引数组
	 * @param types
	 *            类型数组
	 * @param criterion
	 *            查询条件
	 * @return 个数
	 */
	long count(String[] indexes, String[] types, Criterion criterion);
	
	/**
	 * 查询列表
	 * @param index
	 * @param type
	 * @param size
	 * @param queryParams
	 * @return
	 */
	List<Map<String,Object>> filterList(String index,String type,int size,List<QueryItem> queryParams,List<SortParams> sorts);
	
	/**
	 * 查询单个
	 * @param index
	 * @param type
	 * @param queryItem
	 * @return
	 */
	Map<String,Object> filterOneObj(String index,String type,QueryItem queryItem);
	
	/**
	 * 分页获取文档信息
	 * @param index
	 * @param type
	 * @param page
	 * @param pageSize
	 * @param queryParams
	 * @return
	 */
	PageResult<Map<String,Object>> filterPage(String index,String type,QueryParams queryParams);

}
