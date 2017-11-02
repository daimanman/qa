package com.man.erpcenter.sales.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.man.erpcenter.common.utils.ObjectUtil;
import com.man.erpcenter.sales.websocket.WebsocketEndPoint;

@Controller
@RequestMapping("/rr")
public class RRController extends BaseController {

	@Autowired
	private WebsocketEndPoint rrWebsocket;
	
	@RequestMapping("/sendBase")
	public void sendBaseInfo(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> bizParams = getReqParams(request);
		int type = ObjectUtil.parseInt(bizParams.get("type"));
		String content = ObjectUtil.toString(bizParams.get("content"));
		rrWebsocket.sendMsg(type, content);
	}
}
