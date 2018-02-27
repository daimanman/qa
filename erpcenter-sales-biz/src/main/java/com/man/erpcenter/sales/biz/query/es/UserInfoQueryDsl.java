package com.man.erpcenter.sales.biz.query.es;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.man.erpcenter.common.basequery.QueryItem;
import com.man.erpcenter.common.basequery.QueryTypeEnum;
import com.man.erpcenter.common.constants.IdxConstant;
import com.man.erpcenter.common.pageinfo.QueryParams;
import com.man.erpcenter.common.utils.ObjectUtil;

/**
 * 查询解析 类似Mapper文件的地位 将前端的参数构造成符合Elasticsearch 规范的DSL查询语句
 * 
 * @author daixm
 *
 */
public class UserInfoQueryDsl {

	/**
	 * 用户查询列表DSL
	 * 
	 * @param bizParams
	 * @return
	 */
	public static QueryParams parseUserListDsl(Map<String, Object> bizParams) {
		QueryParams dslParams = new QueryParams();
		// 分页信息
		dslParams.setPage(ObjectUtil.parseInt(bizParams.get("page")));
		dslParams.setPageSize(ObjectUtil.parseInt(bizParams.get("pageSize")));

		List<QueryItem> queryItems = new ArrayList<QueryItem>();
		// 业务查询字段
		// nickname like 查询
		String nickName = ObjectUtil.toString(bizParams.get("nickname"));
		if (!ObjectUtil.isNull(nickName)) {
			QueryItem nickNameItem = new QueryItem();
			nickNameItem.matchType = QueryTypeEnum.LIKE.getType();
			nickNameItem.field = "nickname";
			nickNameItem.value = nickName;
			queryItems.add(nickNameItem);
		}

		// UID in 查询
		String uid = ObjectUtil.toString(bizParams.get("uid"));
		if (!ObjectUtil.isNull(uid)) {
			QueryItem uidItem = new QueryItem();
			uidItem.field = "uid";
			uidItem.matchType = QueryTypeEnum.IN.getType();
			uidItem.value = uid;
			queryItems.add(uidItem);
		}

		// city multifield 查询
		String city = ObjectUtil.toString(bizParams.get("city"));
		if (!ObjectUtil.isNull(city)) {
			QueryItem cityItem = new QueryItem();
			cityItem.field = "city,hc";
			cityItem.matchType = QueryTypeEnum.LIKE.getType();
			cityItem.value = city;
			cityItem.andOr = IdxConstant.OR;
			queryItems.add(cityItem);
		}

		// ageStart 年龄起始
		int ageStart = ObjectUtil.parseInt(bizParams.get("ageStart"));
		if (ageStart > 0) {
			QueryItem ageStartItem = new QueryItem();
			ageStartItem.field = "age";
			ageStartItem.matchType = QueryTypeEnum.GTE.getType();
			ageStartItem.value = ageStart;
			queryItems.add(ageStartItem);
		}

		// ageStart 年龄结束
		int ageEnd = ObjectUtil.parseInt(bizParams.get("ageEnd"));
		if (ageEnd > 0) {
			QueryItem ageEndItem = new QueryItem();
			ageEndItem.field = "age";
			ageEndItem.matchType = QueryTypeEnum.LTE.getType();
			ageEndItem.value = ageEnd;
			queryItems.add(ageEndItem);
		}

		// sex 性别
		int sex = ObjectUtil.parseInt(bizParams.get("sex"));
		if(sex > 0){
			QueryItem sexItem = new QueryItem("sex",sex,QueryTypeEnum.IN.getType());
			queryItems.add(sexItem);
		}
		// career 事业 like
		String career = ObjectUtil.toString(bizParams.get("career"));
		if (!ObjectUtil.isNull(career)) {
				QueryItem careerItem  = new QueryItem("career", career,QueryTypeEnum.LIKE.getType());
				queryItems.add(careerItem);
		}
		
		// company 公司 like
		String company = ObjectUtil.toString(bizParams.get("company"));
		if(!ObjectUtil.isNull(company)){
			QueryItem companyItem = new QueryItem("company",company,QueryTypeEnum.LIKE.getType());
			queryItems.add(companyItem);
		}
		dslParams.setQueryItems(queryItems);
		return dslParams;
	}
}
