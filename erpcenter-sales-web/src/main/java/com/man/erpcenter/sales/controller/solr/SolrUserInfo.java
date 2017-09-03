package com.man.erpcenter.sales.controller.solr;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.man.erpcenter.sales.client.po.QuserInfoPo;
import com.man.erpcenter.sales.client.solr.SolrQueryParams;
import com.man.erpcenter.sales.client.solr.SolrSearchResult;
import com.man.erpcenter.sales.controller.BaseController;
import com.man.erpcenter.solrsearch.biz.UserInfoSolrManager;

@Controller
@RequestMapping("/sUserInfo")
public class SolrUserInfo extends BaseController {

	@Autowired
	private UserInfoSolrManager solrManager; 
	
	@RequestMapping("queryUserInfo")
	public void queryUserInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String,Object> params  = getReqParams(request);
		SolrQueryParams solrParams = new SolrQueryParams();
		solrParams.setBizParams(params);
		solrParams.setRows(Long.parseLong(params.get("rows").toString()));
		solrParams.setStart(Long.parseLong(params.get("start").toString()));
		SolrSearchResult<QuserInfoPo> result =  solrManager.querySolrUserInfo(solrParams);
		sendJson(response, result);
	}
}
