package com.man.erpcenter.sales.controller.userinfo;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.man.erpcenter.common.pageinfo.PageResult;
import com.man.erpcenter.common.pageinfo.QueryParams;
import com.man.erpcenter.sales.client.service.QuserInfoService;
import com.man.erpcenter.sales.controller.BaseController;

@Controller
@RequestMapping("/quser")
public class QuserInfoController extends BaseController {

	@Autowired
	private QuserInfoService quserInfoService;
	
	@RequestMapping("/queryList")
	public void  queryList(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String,Object> bizParams = getReqParams(request);
		QueryParams queryParams = JSON.parseObject(JSON.toJSONString(bizParams), QueryParams.class);
		PageResult<Map<String,Object>> pageResult = quserInfoService.queryUserList(queryParams);
		sendJson(response, pageResult);
	}
}
