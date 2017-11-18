package com.man.erpcenter.sales.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	@RequestMapping("/sendBase")
	public void sendBaseInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		rrManager.startBase(1001,0);
		//sendJson(response, "OK");
	}
	
	
	
}
