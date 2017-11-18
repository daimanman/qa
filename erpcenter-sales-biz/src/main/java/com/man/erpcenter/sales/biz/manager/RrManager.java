package com.man.erpcenter.sales.biz.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSON;
import com.man.erpcenter.common.utils.IdWorker;
import com.man.erpcenter.common.utils.ObjectUtil;
import com.man.erpcenter.sales.biz.mapper.RrUserPoMapper;
import com.man.erpcenter.sales.biz.util.HttpRequestUtil;
import com.man.erpcenter.sales.biz.websocket.WebsocketEndPoint;
import com.man.erpcenter.sales.client.po.RrUserPo;

public class RrManager {

	
	private Map<String,String> cookie;
	
	private Map<String,Object> baseParams;
	
	private Map<String,Boolean> baseFlagMap  = new HashMap<String,Boolean>();
	
	//请求基本信息
	private String baseInfoUrl;
	
	public Map<Integer,Integer> resultNumMap = new HashMap<Integer, Integer>();
	
	@Autowired
	private WebsocketEndPoint rrWebsocket;
	
	
	
	public String getBaseInfoUrl() {
		return baseInfoUrl;
	}

	public void setBaseInfoUrl(String baseInfoUrl) {
		this.baseInfoUrl = baseInfoUrl;
	}
	@Autowired
	private RrUserPoMapper rrUserPoMapper;
	
	
	@Autowired
	private IdWorker idWorker;
	
	
	public void init(){
		ObjectUtil util = new ObjectUtil();
		cookie = util.getHeadersMap("RR_c.properties");
		baseParams = util.getHttpParam("RR_q.properties");
	}
	
	
	private List<RrUserPo> converRrUser(List<Map<String,Object>> users,int schoolId,int gender){
		List<RrUserPo> rusers = new ArrayList<RrUserPo>();
		if(null != users && users.size() > 0){
			for(Map<String,Object> o:users){
				RrUserPo po = new RrUserPo();
				po.setBirthdate(null);
				po.setGender(gender);
				po.setId(idWorker.nextId());
				po.setLogoImg(ObjectUtil.toString(o.get("logoImg")));
				po.setRid(ObjectUtil.parseLong(o.get("rid")));
				po.setName(ObjectUtil.toString(o.get("name")));
				po.setPopValue(ObjectUtil.parseInt(o.get("popValue")));
				po.setSchoolId(schoolId);
				rusers.add(po);
			}
		}
		return rusers;
	}
	public void addRrUserBatch(String json){
		try{
		   
			Map<String,Object> jsonMap = JSON.parseObject(json, Map.class);
			int schoolId = ObjectUtil.parseInt(jsonMap.get("schoolId"));
			int gender = ObjectUtil.parseInt(jsonMap.get("gender"));
			List<Map<String,Object>> users = ObjectUtil.castListObj(jsonMap.get("users"));
			List<RrUserPo> rusers = converRrUser(users, schoolId, gender);
			int resultNum = ObjectUtil.parseInt(jsonMap.get("resultNum"));
			if(resultNumMap.get(schoolId) == null && resultNum > 0){
				resultNumMap.put(schoolId, resultNum);
			}
			if(null != rusers && rusers.size() > 0){
				try{
				rrUserPoMapper.addRrUserBatch(rusers);
				baseFlagMap.put(schoolId+"_"+gender, true);
				}catch(Exception e){
					System.out.println(schoolId+"----"+gender+"--------is over");
					baseFlagMap.put(schoolId+"_"+gender, false);
				}
				rrUserPoMapper.updateLog(jsonMap);
			}else{
				int offset = ObjectUtil.parseInt(jsonMap.get("offset"));
				rrUserPoMapper.addErrLog(jsonMap);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void startBase(Integer schoolId,Integer gender){
		Map<String,Object> bizParams = new HashMap<String,Object>();
		bizParams.put("schoolId", schoolId);
		bizParams.put("gender",gender);
		String key = schoolId+"_"+gender;
	
		
		int startOffset = rrUserPoMapper.getOffsetBySidAndGender(bizParams);
		System.out.println("----------startOffset-----------"+startOffset);
		while((baseFlagMap.get(key) == null) || (baseFlagMap.get(key)) ){
			
			baseParams.put("offset",startOffset);
			startOffset += 10;
			
			try {
				String content = HttpRequestUtil.sendHttpPost(baseInfoUrl, baseParams, cookie);
				WebSocketSession session =  rrWebsocket.sendMsg(WebsocketEndPoint.RR_USER_BASE, content);
				if(null == session || !session.isOpen() ){
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
				try {
					Thread.sleep(1000*5);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			
		}
	}
	
	
	
	
	
}
