package com.man.erpcenter.sales.biz.query.es;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.man.erpcenter.common.basequery.QueryItem;
import com.man.erpcenter.common.basequery.QueryTypeEnum;
import com.man.erpcenter.common.constants.IdxConstant;
import com.man.erpcenter.common.pageinfo.QueryParams;
import com.man.erpcenter.common.pageinfo.SortParams;
import com.man.erpcenter.common.utils.ObjectUtil;

/**
 * 查询解析 类似Mapper文件的地位 将前端的参数构造成符合Elasticsearch 规范的DSL查询语句
 * 
 * @author daixm
 *
 */
public class EmotInfoQueryDsl extends BaseQueryDsl {

	/**
	 * 用户查询列表DSL
	 * 
	 * @param bizParams
	 * @return
	 */
	public static QueryParams parseListDsl(Map<String, Object> bizParams) {
		QueryParams dslParams = new QueryParams();
		// 分页信息
		dslParams.setPage(ObjectUtil.parseInt(bizParams.get("page")));
		dslParams.setPageSize(ObjectUtil.parseInt(bizParams.get("pageSize")));

		List<QueryItem> queryItems = new ArrayList<QueryItem>();
		// 业务查询字段
		// content like 查询
		String content = ObjectUtil.toString(bizParams.get("content"));
		if (!ObjectUtil.isNull(content)) {
			QueryItem nickNameItem = new QueryItem("content", content, QueryTypeEnum.LIKE.getType());
			queryItems.add(nickNameItem);
		}

		// UID in 查询
		String uid = ObjectUtil.toString(bizParams.get("uid"));
		if (!ObjectUtil.isNull(uid)) {
			QueryItem uidItem = new QueryItem("uid", uid, QueryTypeEnum.IN.getType());
			queryItems.add(uidItem);
		}

		// lbs_name multifield 查询
		String lbsName = ObjectUtil.toString(bizParams.get("lbsName"));
		if (!ObjectUtil.isNull(lbsName)) {
			QueryItem cityItem = new QueryItem("lbs_name,lbs_idname", lbsName, QueryTypeEnum.LIKE.getType());
			cityItem.andOr = IdxConstant.OR;
			queryItems.add(cityItem);
		}

		int idStart = ObjectUtil.parseInt(bizParams.get("idStart"));
		if (idStart > 0) {
			QueryItem ageStartItem = new QueryItem("id", idStart, QueryTypeEnum.GTE.getType());
			queryItems.add(ageStartItem);
		}

		int idEnd = ObjectUtil.parseInt(bizParams.get("idEnd"));
		if (idEnd > 0) {
			QueryItem ageEndItem = new QueryItem("id", idEnd, QueryTypeEnum.LTE.getType());
			queryItems.add(ageEndItem);
		}

		dslParams.setQueryItems(queryItems);
		dslParams.setSorts(parseSortParams(bizParams));
		return dslParams;
	}


}
