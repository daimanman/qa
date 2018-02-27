package com.man.erpcenter.sales.controller.userinfo;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.man.erpcenter.common.pageinfo.PageResult;
import com.man.erpcenter.sales.biz.query.es.EmotInfoQueryDsl;
import com.man.erpcenter.sales.biz.query.es.QphotoInfoQueryDsl;
import com.man.erpcenter.sales.biz.query.es.UserInfoQueryDsl;
import com.man.erpcenter.sales.client.service.QemotInfoService;
import com.man.erpcenter.sales.client.service.QphotoInfoService;
import com.man.erpcenter.sales.client.service.QuserInfoService;
import com.man.erpcenter.sales.controller.BaseController;

@Controller
@RequestMapping("/qes")
public class QqInfoController extends BaseController {

	@Autowired
	private QuserInfoService quserInfoService;
	
	@Autowired
	private QemotInfoService qemotInfoService;
	
	@Autowired
	private QphotoInfoService qphotoInfoService;
	
	@RequestMapping("/queryUserList")
	public void  queryUserList(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String,Object> bizParams = getReqParams(request);
		PageResult<Map<String,Object>> pageResult = quserInfoService.queryUserList(UserInfoQueryDsl.parseUserListDsl(bizParams));
		sendJson(response, pageResult);
	}
	
	@RequestMapping("/queryEmotList")
	public void  queryEmotList(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String,Object> bizParams = getReqParams(request);
		PageResult<Map<String,Object>> pageResult = qemotInfoService.queryEmotList(EmotInfoQueryDsl.parseListDsl(bizParams));
		sendJson(response, pageResult);
	}
	
	@RequestMapping("/queryPhotList")
	public void  queryPhotList(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String,Object> bizParams = getReqParams(request);
		PageResult<Map<String,Object>> pageResult = qphotoInfoService.queryPhotoList(QphotoInfoQueryDsl.parseListDsl(bizParams));
		sendJson(response, pageResult);
	}
	
	
	
	
}
