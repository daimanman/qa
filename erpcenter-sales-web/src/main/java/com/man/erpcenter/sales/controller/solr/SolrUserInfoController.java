package com.man.erpcenter.sales.controller.solr;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.man.erpcenter.common.utils.ObjectUtil;
import com.man.erpcenter.sales.client.solr.SolrQueryParams;
import com.man.erpcenter.sales.client.solr.SolrSearchResult;
import com.man.erpcenter.sales.controller.BaseController;
import com.man.erpcenter.solrsearch.biz.PhotoInfoSolrManager;
import com.man.erpcenter.solrsearch.biz.UserInfoSolrManager;
import com.man.erpcenter.solrsearch.client.SolrPhotoInfo;
import com.man.erpcenter.solrsearch.client.SolrUserInfo;

@Controller
@RequestMapping("/solr")
public class SolrUserInfoController extends BaseController {

	@Autowired
	private UserInfoSolrManager userSolrManager; 
	@Autowired
	private PhotoInfoSolrManager photoSolrManager;
	
	@RequestMapping("queryUserInfo")
	public void queryUserInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String,Object> params  = getReqParams(request);
		SolrQueryParams solrParams = new SolrQueryParams();
		initSolrBeforeQueryParams(solrParams, params);
		SolrSearchResult<SolrUserInfo> result =  userSolrManager.querySolrUserInfo(solrParams);
		initSolrAfterQueryParams(result, params);
		sendJson(response, result);
	}
	
	@RequestMapping("/queryPhotoInfo")
	public void queryPhotoInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String,Object> params = getReqParams(request);
		SolrQueryParams solrParams = new SolrQueryParams();
		initSolrBeforeQueryParams(solrParams, params);
		SolrSearchResult<SolrPhotoInfo> result = photoSolrManager.querySolrPhotoInfo(solrParams);
		initSolrAfterQueryParams(result, params);
		sendJson(response, result);
	}
}
