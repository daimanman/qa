package com.man.erpcenter.solrsearch.biz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import com.man.erpcenter.common.utils.ObjectUtil;
import com.man.erpcenter.sales.client.solr.SolrQueryParams;
import com.man.erpcenter.sales.client.solr.SolrSearchResult;
import com.man.erpcenter.solrsearch.client.SolrImgInfo;

public class ImgSolrManager extends SolrSearchCenterManager {

	@Override
	public String getCoreName() {
		return SolrConstaint.IMG_CORE_NAME;
	}
	

	public  SolrSearchResult<SolrImgInfo> querySolrImgInfo(SolrQueryParams solrQueryParams){
		SolrSearchResult<SolrImgInfo> solrSearchResult = new SolrSearchResult<SolrImgInfo>();
		HttpSolrClient solrClient = getSolrClient();
		solrQueryParams.setQ(parseQueryStr(solrQueryParams.getBizParams()));
		SolrQuery solrQuery = parseSolrQuery(solrQueryParams);
		QueryResponse resp;
		try {
			resp = solrClient.query(solrQuery);
			SolrDocumentList results =  resp.getResults();
			solrSearchResult.setTotal(results.getNumFound());
			solrSearchResult.setStart(results.getStart());

			List<SolrImgInfo> datas = new ArrayList<SolrImgInfo>();
			for(SolrDocument doc:results){
				SolrImgInfo img = new SolrImgInfo();
				img.id = ObjectUtil.parseLong(doc.get("id"));
				img.cameratype = ObjectUtil.toString(doc.get("cameratype"));
				img.photoId = ObjectUtil.parseLong(doc.get("photo_id"));
				img.poiname =  ObjectUtil.toString(doc.get("poiname"));
				img.uid = ObjectUtil.parseLong(doc.get("uid"));
				img.uploadtime = ObjectUtil.toString(doc.get("uploadtime"));
				img.url = ObjectUtil.toString(doc.get("url"));
				datas.add(img);
			}
			solrSearchResult.setDatas(datas);
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
		
		return solrSearchResult;
	}
	
	private String parseQueryStr(Map<String,Object> params){
		StringBuffer sb = new StringBuffer("");
		String uid = ObjectUtil.toString(params.get("uid"));
		String photoId = ObjectUtil.toString(params.get("photoId"));
		if(!"".equals(uid)){
			sb.append(" AND ").append("uid:").append(uid);
		}
		
		if(!"".equals(photoId)){
			sb.append(" AND photo_id:").append(photoId);
		}

		if("".equals(sb.toString())){
			sb.append("*:*");
			return sb.toString();
		}else{
			return sb.toString().replaceFirst("AND","");
		}
	}

}
