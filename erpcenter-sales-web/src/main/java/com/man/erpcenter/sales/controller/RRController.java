package com.man.erpcenter.sales.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.man.erpcenter.sales.biz.manager.RrManager;
import com.man.erpcenter.sales.biz.websocket.WebsocketEndPoint;

@Controller
@RequestMapping("/rr")
public class RRController extends BaseController {

	@Autowired
	private WebsocketEndPoint rrWebsocket;
	
	@Autowired
	private RrManager rrManager;
	
	private Logger logger  = LoggerFactory.getLogger(RRController.class);

	@RequestMapping("/sendBase")
	public void sendBaseInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//rrManager.startBase(1001,0);
	}
	
	@RequestMapping("/log")
	public void testLog(HttpServletRequest request ,HttpServletResponse response) throws IOException{
		logger.error(getReqParams(request).toString()+"-------error------");
		logger.debug(getReqParams(request).toString()+"---------debug-------");
		logger.info(getReqParams(request).toString()+"----------info---------");
		sendJson(response, getReqParams(request));
	}
	
	
	
}
