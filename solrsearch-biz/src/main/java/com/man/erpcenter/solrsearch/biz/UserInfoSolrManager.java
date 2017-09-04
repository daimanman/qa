package com.man.erpcenter.solrsearch.biz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import com.man.erpcenter.common.utils.ObjectUtil;
import com.man.erpcenter.sales.client.po.QuserInfoPo;
import com.man.erpcenter.sales.client.solr.SolrQueryParams;
import com.man.erpcenter.sales.client.solr.SolrSearchResult;
import com.man.erpcenter.solrsearch.client.SolrUserInfo;

public class UserInfoSolrManager extends SolrSearchCenterManager {

	@Override
	public String getCoreName() {
		return SolrConstaint.USER_INFO_CORE_NAME;
	}
	
	public SolrSearchResult<SolrUserInfo> querySolrUserInfo(SolrQueryParams bizParams){
		SolrSearchResult<SolrUserInfo> solrSearchResult = new SolrSearchResult<SolrUserInfo>();
		HttpSolrClient solrClient = getSolrClient();
		SolrQuery solrQuery = parseSolrQuery(bizParams);
		QueryResponse resp;
		try {
			resp = solrClient.query(solrQuery);
			SolrDocumentList results =  resp.getResults();
			solrSearchResult.setTotal(results.getNumFound());
			solrSearchResult.setStart(results.getStart());
			List<SolrUserInfo> datas = new ArrayList<SolrUserInfo>();
			for(SolrDocument doc:results){
				SolrUserInfo solrUserInfo = new SolrUserInfo();
				solrUserInfo.age = ObjectUtil.parseInt(doc.get("age"));
				solrUserInfo.birthday  = ObjectUtil.toString(doc.get("birthday"));
				solrUserInfo.birthyear = ObjectUtil.parseInt(doc.get("birthyear"));
				solrUserInfo.bloodtype = ObjectUtil.parseInt(doc.get("bloodtype"));
				solrUserInfo.career = ObjectUtil.toString(doc.get("career"));
				solrUserInfo.city = ObjectUtil.toString(doc.get("city"));
			    solrUserInfo.company = ObjectUtil.toString(doc.get("company"));
			    solrUserInfo.country = ObjectUtil.toString(doc.get("country"));
			    solrUserInfo.hc = ObjectUtil.toString(doc.get("hc"));
			    solrUserInfo.hco = ObjectUtil.toString(doc.get("hco"));
			    solrUserInfo.hp = ObjectUtil.toString(doc.get("hp"));
			    solrUserInfo.id = ObjectUtil.parseLong(doc.get("id"));
			    solrUserInfo.marriage = ObjectUtil.parseInt(doc.get("marriage"));
			    solrUserInfo.nickname = ObjectUtil.toString(doc.get("nickname"));
			    solrUserInfo.province = ObjectUtil.toString(doc.get("province"));
			    solrUserInfo.sex = ObjectUtil.parseInt(doc.get("sex"));
			    solrUserInfo.uid = ObjectUtil.parseLong(doc.get("uid"));
			    datas.add(solrUserInfo);
			}
			solrSearchResult.setDatas(datas);
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
		
		return solrSearchResult;
	}

}
