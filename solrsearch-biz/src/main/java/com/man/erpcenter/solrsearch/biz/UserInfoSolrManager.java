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

import com.man.erpcenter.sales.client.po.QuserInfoPo;
import com.man.erpcenter.sales.client.solr.SolrQueryParams;
import com.man.erpcenter.sales.client.solr.SolrSearchResult;

public class UserInfoSolrManager extends SolrSearchCenterManager {

	@Override
	public String getCoreName() {
		return SolrConstaint.USER_INFO_CORE_NAME;
	}
	
	
	
	public SolrSearchResult<QuserInfoPo> querySolrUserInfo(SolrQueryParams bizParams){
		SolrSearchResult<QuserInfoPo> solrSearchResult = new SolrSearchResult<QuserInfoPo>();
		HttpSolrClient solrClient = getSolrClient();
		SolrQuery solrQuery = parseSolrQuery(bizParams);
		QueryResponse resp;
		try {
			resp = solrClient.query(solrQuery);
			SolrDocumentList results =  resp.getResults();
			solrSearchResult.setTotal(results.getNumFound());
			solrSearchResult.setStart(results.getStart());
			solrSearchResult.setRows(bizParams.getRows());
			List<QuserInfoPo> datas = new ArrayList<QuserInfoPo>();
			for(SolrDocument doc:results){
				QuserInfoPo userInfo = new QuserInfoPo();
				userInfo.setId(Double.parseDouble(doc.get("id").toString()));
				userInfo.setAge(doc.get("age").toString());
				userInfo.setBirthday(doc.get("birthday").toString());
				userInfo.setBirthyear(doc.get("birthyear").toString());
				userInfo.setBloodtype(doc.get("bloodtype").toString());
				userInfo.setCareer(doc.get("career").toString());
				userInfo.setCity(doc.get("city").toString());
				userInfo.setCompany(doc.get("company").toString());
				userInfo.setCountry(doc.get("country").toString());
				userInfo.setHc(doc.get("hc").toString());
				userInfo.setHco(doc.get("hco").toString());
				userInfo.setHp(doc.get("hp").toString());
				userInfo.setMarriage(doc.get("marriage").toString());
				userInfo.setNickname(doc.get("nickname").toString());
				userInfo.setProvince(doc.get("province").toString());
				userInfo.setSex(doc.get("sex").toString());
				userInfo.setUid(Double.parseDouble(doc.get("uid").toString()));
				datas.add(userInfo);
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
