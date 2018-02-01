package com.man.erpcenter.sales.controller.es;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private Logger logger  = LoggerFactory.getLogger(EsController.class);
	
	private static Map<String,Boolean> tableMap = new HashMap<String,Boolean>();
	
	@Autowired
	private ElasticSearchManager elasticSearchManager;
	
	@RequestMapping("/getDoc")
	public void getDoc(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String,Object> bizParams = getReqParams(request);
		String id = ObjectUtil.toString(bizParams.get("id"));
		sendJson(response,elasticSearchManager.get(ElasticSearchService.QQ_INDEX,ElasticSearchService.QQ_USER_TYPE, id));
	}
	
	@RequestMapping("/importMySqlData")
	public void importMySqlData(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String,Object> bizParams = getReqParams(request);
		String tableName = ObjectUtil.toString(bizParams.get("tableName"));
		if(!"".equals(tableName) ){
			if(tableMap.get(tableName) == null || !tableMap.get(tableName)){
				tableMap.put(tableName, true);
				elasticSearchManager.importMySqlData02(tableName);
			}
			
		}
		sendJson(response, "is ok");
	}
	
	@RequestMapping("/log")
	public void log(HttpServletRequest request,HttpServletResponse response) throws IOException{
		logger.info("this is info ----");
		logger.warn("this is warn --------");
		logger.debug("this is deebug -------");
		logger.error("this is error----------");
		sendJson(response, "is log");
	}
}
