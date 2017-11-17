package com.man.erpcenter.sales.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.man.erpcenter.common.utils.ObjectUtil;
import com.man.erpcenter.sales.biz.manager.RrManager;
import com.man.erpcenter.sales.biz.websocket.WebsocketEndPoint;
import com.man.erpcenter.sales.client.service.RedisInfoService;

@Controller
@RequestMapping("/rr")
public class RRController extends BaseController {

	@Autowired
	private WebsocketEndPoint rrWebsocket;
	
	@Autowired
	private RedisInfoService  redisInfoService;
	
	@Autowired
	private RrManager rrManager;
	
	private Logger logger  = LoggerFactory.getLogger(RRController.class);
	
	private List<String> keys = new ArrayList<String>();
	
	

	@RequestMapping("/sendBase")
	public void sendBaseInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//rrManager.startBase(1001,0);
	}
	
	@RequestMapping("/log")
	public void testLog(HttpServletRequest request ,HttpServletResponse response) throws IOException{
		sendJson(response, getReqParams(request));
	}
	
	@RequestMapping("/get")
	public void get(HttpServletRequest request ,HttpServletResponse response) throws IOException{
		Map<String,Object> params = getReqParams(request);
		String key = ObjectUtil.toString(params.get("key"));
		params.put("val", redisInfoService.get(key));
		sendJson(response, params);
	}
	
	@RequestMapping("/set")
	public void set(HttpServletRequest request ,HttpServletResponse response) throws IOException{
		Map<String,Object> params = getReqParams(request);
		String key = ObjectUtil.toString(params.get("key"));
		String val = ObjectUtil.toString(params.get("val"));
		redisInfoService.set(key, val);
		sendJson(response, getReqParams(request));
	}
	
	@RequestMapping("/press")
	public void ab(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String uuid = UUID.randomUUID().toString();
		redisInfoService.set(uuid, uuid);
		keys.add(uuid);
	}
	
	@RequestMapping("/getAb")
	public void getAb(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String,Object> errorsMap = new HashMap<String,Object>();
		for(String key:keys){
			if(null == key){
				continue;
			}
			String val = redisInfoService.get(key);
			if(!key.equals(val)){
				errorsMap.put(key,val);
			}
		}
		sendJson(response, errorsMap);
	}
	
	@RequestMapping("/initKeys")
	public void initKeys(HttpServletRequest request,HttpServletResponse response) throws IOException{
		keys = new ArrayList<String>();
		sendJson(response, keys.size());
	}
	
	@RequestMapping("/getSize")
	public void getSize(HttpServletRequest request,HttpServletResponse response) throws IOException{
		sendJson(response, keys.size());
	}
	@RequestMapping("/getKeys")
	public void getKeys(HttpServletRequest request,HttpServletResponse response) throws IOException{
		sendJson(response, keys);
	}
	
	
	
	
	
}
