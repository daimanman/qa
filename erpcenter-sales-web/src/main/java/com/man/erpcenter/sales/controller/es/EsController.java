package com.man.erpcenter.sales.controller.es;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.man.erpcenter.common.utils.ObjectUtil;
import com.man.erpcenter.elasticsearch.service.ElasticSearchService;
import com.man.erpcenter.sales.biz.manager.ElasticSearchManager;
import com.man.erpcenter.sales.controller.BaseController;

@Controller
@RequestMapping("/es")
public class EsController  extends BaseController {

	@Autowired
	private ElasticSearchManager elasticSearchManager;
	@RequestMapping("/getDoc")
	public void getDoc(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String,Object> bizParams = getReqParams(request);
		String id = ObjectUtil.toString(bizParams.get("id"));
		sendJson(response,elasticSearchManager.get(ElasticSearchService.QQ_INDEX,ElasticSearchService.QQ_USER_TYPE, id));
	}
}
