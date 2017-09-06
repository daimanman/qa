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
import com.man.erpcenter.sales.client.solr.SolrQueryParams;
import com.man.erpcenter.sales.client.solr.SolrSearchResult;
import com.man.erpcenter.solrsearch.client.SolrPhotoInfo;

public class PhotoInfoSolrManager extends SolrSearchCenterManager {

	@Override
	public String getCoreName() {
		return SolrConstaint.PHOTO_CORE_NAME;
	}
	
	public SolrSearchResult<SolrPhotoInfo> querySolrPhotoInfo(SolrQueryParams solrQueryParams){
		SolrSearchResult<SolrPhotoInfo> solrSearchResult = new SolrSearchResult<SolrPhotoInfo>();
		HttpSolrClient solrClient = getSolrClient();
		solrQueryParams.setQ("*:*");
		SolrQuery solrQuery = parseSolrQuery(solrQueryParams);
		QueryResponse resp;
		
		try {
			resp = solrClient.query(solrQuery);
			SolrDocumentList results =  resp.getResults();
			solrSearchResult.setTotal(results.getNumFound());
			solrSearchResult.setStart(results.getStart());
			List<SolrPhotoInfo> datas = new ArrayList<SolrPhotoInfo>();
			for(SolrDocument doc:results){
				SolrPhotoInfo photo = new SolrPhotoInfo();
				photo.allowAccess = ObjectUtil.parseInt(doc.get("allow_access"));
				photo.comment = ObjectUtil.parseInt(doc.get("comment"));
				photo.createtime = ObjectUtil.parseLong(doc.get("createtime"));
				photo.desc = ObjectUtil.toString(doc.get("desc"));
				photo.id = ObjectUtil.parseLong(doc.get("id"));
				photo.name = ObjectUtil.toString(doc.get("name"));
				photo.pre = ObjectUtil.toString(doc.get("pre"));
				photo.topicid = ObjectUtil.toString(doc.get("topicid"));
				photo.totalnum = ObjectUtil.parseInt(doc.get("totalnum"));
				photo.uid = ObjectUtil.parseLong(doc.get("uid"));
				datas.add(photo);
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
