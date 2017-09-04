package com.man.erpcenter.sales.biz.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.man.erpcenter.common.utils.ObjectUtil;
import com.man.erpcenter.sales.biz.util.YhHttpUtil;
import com.man.erpcenter.sales.client.constant.QcodeEnum;

public  class BaseQqManager {

	// base info uids
	public List<String> uids = new ArrayList<String>();
	
	//shuo info uids
	public static List<String> emotUids = new ArrayList<String>();
	
	//msg info uids
	public static List<String> msgUids = new ArrayList<String>();

	//photo info uids 
	public static List<String> photoUids = new ArrayList<String>();
	
	//img info uids
	public static List<String> imgUids  = new ArrayList<String>();
	
	//visitor uids
	public static List<String> visitUids = new ArrayList<String>();
	
	//存储参数
	public Map<String, Map<String, Object>> paramsMap = new HashMap<String,Map<String,Object>>();

	//存储cookie
	public Map<String, Map<String, String>> cookiesMap = new HashMap<String,Map<String,String>>();
	
	//初始化的uids
	public Set<String> initUids = new HashSet<String>();
	
	public Map<String,Integer> countQMap ;
	
	public static String INFO_KEY = "infoKey";
	
	public static String EMOT_KEY = "emotKey";
	
	//emot num
	public static int DEFAULT_NUM = 30;
	
	//msg num 
	public static int DEFAULT_MSG_NUM = 20;
	
	//img num
	
	public static int DEFAULT_IMG_NUM = 500;
	
	public static int CODE_VERSION = 1;
	
	public static int REPLYNUM = 100;
	
	public static String ALLOW_ACCESS="1";
	
	public String baseInfoUrl;
	
	public String emotInfoUrl;
	
	public String msgInfoUrl;
	
	public String photoInfoUrl;
	 
	public String imgInfoUrl;
	
	public String visitUrl;
	
	
	public static int startWebFlag = 0;
	
	
	public static int startMsgWebFlag= 0;
	

	
	public String getVisitUrl() {
		return visitUrl;
	}

	public void setVisitUrl(String visitUrl) {
		this.visitUrl = visitUrl;
	}

	public String getPhotoInfoUrl() {
		return photoInfoUrl;
	}

	public void setPhotoInfoUrl(String photoInfoUrl) {
		this.photoInfoUrl = photoInfoUrl;
	}

	public String getImgInfoUrl() {
		return imgInfoUrl;
	}

	public void setImgInfoUrl(String imgInfoUrl) {
		this.imgInfoUrl = imgInfoUrl;
	}

	public String getMsgInfoUrl() {
		return msgInfoUrl;
	}

	public void setMsgInfoUrl(String msgInfoUrl) {
		this.msgInfoUrl = msgInfoUrl;
	}

	public String getBaseInfoUrl() {
		return baseInfoUrl;
	}

	public void setBaseInfoUrl(String baseInfoUrl) {
		this.baseInfoUrl = baseInfoUrl;
	}

	public String getEmotInfoUrl() {
		return emotInfoUrl;
	}

	public void setEmotInfoUrl(String emotInfoUrl) {
		this.emotInfoUrl = emotInfoUrl;
	}

	public List<String> getUids() {
		return uids;
	}

	public void setUids(List<String> uids) {
		this.uids = uids;
	}

	public Map<String, Map<String, Object>> getParamsMap() {
		return paramsMap;
	}

	public void setParamsMap(Map<String, Map<String, Object>> paramsMap) {
		this.paramsMap = paramsMap;
	}

	public Map<String, Map<String, String>> getCookiesMap() {
		return cookiesMap;
	}

	public void setCookiesMap(Map<String, Map<String, String>> cookiesMap) {
		this.cookiesMap = cookiesMap;
	}
	
	public Map<String,Object> getQzoneEmotInfoContent(String uid,int pos){
		while(startWebFlag == 0){
			//一直循环
			System.out.println("---------等待Session---------");
			try {
				Thread.sleep(1000*60*10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(emotUids != null && emotUids.size() > 0 ){
			long systime = System.currentTimeMillis()%emotUids.size();
			int index = (int)systime;
			String effectUid = emotUids.get(index);
			//获取参数
			Map<String,Object> originMap = paramsMap.get(effectUid);
			Map<String,Object> newParamMap = new HashMap<String, Object>();
			newParamMap.put("g_tk", originMap.get("g_tk"));
			newParamMap.put("qzonetoken", originMap.get("qzonetoken"));
			newParamMap.put("format","json");
			newParamMap.put("pos", pos);
			newParamMap.put("num", DEFAULT_NUM);
			newParamMap.put("code_version", CODE_VERSION);
			newParamMap.put("replynum", REPLYNUM);
			newParamMap.put("ftype", 0);
			newParamMap.put("sort", 0);
			newParamMap.put("need_private_comment", 1);
			newParamMap.put("uin", uid);
			
			//获取会话信息
			Map<String,String> cookieMap  = cookiesMap.get(effectUid);
			
			String content = YhHttpUtil.sendHttpGetWithRetry(getEmotInfoUrl(), newParamMap,cookieMap);
			Map<String, Object> contentMap = JSON.parseObject(content, Map.class);
			Object code = contentMap.get("code");
			Object subcode = contentMap.get("subcode");
			String codeStr = code != null ? code.toString() : "";
			String subcodeStr = subcode != null ? subcode.toString() : "";
			String message = ObjectUtil.toString(contentMap.get("message"));
			while( checkOffen(codeStr, subcodeStr,message) && (emotUids.size()) > 0){
				emotUids.remove(effectUid);
				return getQzoneEmotInfoContent(uid,pos);
			}
			return contentMap;
			
		}else{
			System.out.println("################### emotInfo is over ######################");
		}
		return null;
	}
	
	public Map<String,Object> getQzoneMsgInfoContent(String uid,int pos){
		while(startWebFlag == 0){
			//一直循环
			System.out.println("--------- getQzoneMsgInfoContent 等待 Session---------");
			try {
				Thread.sleep(1000*60*10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(msgUids != null && msgUids.size() > 0 ){
			long systime = System.currentTimeMillis()%msgUids.size();
			int index = (int)systime;
			String effectUid = msgUids.get(index);
			//获取参数
			Map<String,Object> originMap = paramsMap.get(effectUid);
			Map<String,Object> newParamMap = new HashMap<String, Object>();
			newParamMap.put("g_tk", originMap.get("g_tk"));
			newParamMap.put("qzonetoken", originMap.get("qzonetoken"));
			newParamMap.put("format","json");
			//newParamMap.put("pos", pos);
			newParamMap.put("num",DEFAULT_MSG_NUM);
			newParamMap.put("hostUin", uid);
			newParamMap.put("uin", effectUid);
			newParamMap.put("inCharset", "utf-8");
			newParamMap.put("outCharset", "utf-8");
			newParamMap.put("start",pos);
			newParamMap.put("essence",1);
			newParamMap.put("ref", "qzone");
			
			//获取会话信息
			Map<String,String> cookieMap  = cookiesMap.get(effectUid);
			
			String content = YhHttpUtil.sendHttpGetWithRetry(getMsgInfoUrl(), newParamMap,cookieMap);
			Map<String, Object> contentMap = JSON.parseObject(content, Map.class);
			Object code = contentMap.get("code");
			Object subcode = contentMap.get("subcode");
			String codeStr = code != null ? code.toString() : "";
			String subcodeStr = subcode != null ? subcode.toString() : "";
			String message = ObjectUtil.toString(contentMap.get("message"));
			while( checkOffen(codeStr, subcodeStr,message) && (msgUids.size()) > 0){
				msgUids.remove(effectUid);
				System.out.println(content);
				return getQzoneMsgInfoContent(uid,pos);
			}
			return contentMap;
			
		}else{
			System.out.println("################### msgInfo is over ######################");
		}
		return null;
	}
	
	public Map<String,Object> getQzoneImgInfo(String uid,int pos,String topicId){
		while(startWebFlag == 0){
			//一直循环
			System.out.println("--------- getQzoneImgInfo 等待 Session---------");
			try {
				Thread.sleep(1000*60*10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(photoUids != null && photoUids.size() > 0 ){
			long systime = System.currentTimeMillis()%photoUids.size();
			int index = (int)systime;
			String effectUid = photoUids.get(index);
			//获取参数
			Map<String,Object> originMap = paramsMap.get(effectUid);
			Map<String,Object> newParamMap = new HashMap<String, Object>();
			newParamMap.put("g_tk", originMap.get("g_tk"));
			newParamMap.put("qzonetoken", originMap.get("qzonetoken"));
			newParamMap.put("format","json");
			newParamMap.put("num",DEFAULT_MSG_NUM);
			newParamMap.put("hostUin", uid);
			newParamMap.put("topicId",topicId);
			newParamMap.put("uin",effectUid);
			newParamMap.put("pageNum",DEFAULT_IMG_NUM);
			newParamMap.put("pageStart",pos);
			newParamMap.put("mode","0");
			newParamMap.put("idcNum","0");
			newParamMap.put("noTopic","0");
			newParamMap.put("skipCmtCount","0");
			newParamMap.put("singleurl","1");
			newParamMap.put("batchId","");
			newParamMap.put("notice","0");
			newParamMap.put("appid","4");
			newParamMap.put("inCharset","utf-8");
			newParamMap.put("outCharset","utf-8");
			newParamMap.put("source","qzone");
			newParamMap.put("plat","qzone");
			newParamMap.put("outstyle","json");
			newParamMap.put("format","json");
			newParamMap.put("json_esc","1");
			
			//获取会话信息
			Map<String,String> cookieMap  = cookiesMap.get(effectUid);
			
			String content = YhHttpUtil.sendHttpGetWithRetry(getImgInfoUrl(), newParamMap,cookieMap);
			Map<String, Object> contentMap = JSON.parseObject(content, Map.class);
			Object code = contentMap.get("code");
			Object subcode = contentMap.get("subcode");
			String codeStr = code != null ? code.toString() : "";
			String subcodeStr = subcode != null ? subcode.toString() : "";
			String message = ObjectUtil.toString(contentMap.get("message"));
			while( checkOffen(codeStr, subcodeStr,message) && (photoUids.size()) > 0){
				photoUids.remove(effectUid);
				return getQzoneImgInfo(uid,pos,topicId);
			}
			return contentMap;
			
		}else{
			System.out.println("################### imgInfo is over ######################");
		}
		return null;
	}
	
	
	public Map<String,Object> getQzonePhotoInfo(String uid){
		while(startWebFlag == 0){
			//一直循环
			System.out.println("--------- getQzoneImgInfo 等待 Session---------");
			try {
				Thread.sleep(1000*60);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(photoUids != null && photoUids.size() > 0 ){
			long systime = System.currentTimeMillis()%photoUids.size();
			int index = (int)systime;
			String effectUid = photoUids.get(index);
			//获取参数
			Map<String,Object> originMap = paramsMap.get(effectUid);
			Map<String,Object> newParamMap = new HashMap<String, Object>();
			newParamMap.put("g_tk", originMap.get("g_tk"));
			newParamMap.put("qzonetoken", originMap.get("qzonetoken"));
			newParamMap.put("format","json");
			newParamMap.put("hostUin",uid);
			newParamMap.put("uin",effectUid);
			newParamMap.put("appid","4");
			newParamMap.put("inCharset","utf-8");
			newParamMap.put("outCharset","utf-8");
			newParamMap.put("source","qzone");
			newParamMap.put("plat","qzone");
			newParamMap.put("notice","0");
			newParamMap.put("filter","1");
			newParamMap.put("handset","4");
			newParamMap.put("pageNumModeSort","40");
			newParamMap.put("pageNumModeClass","15");
			newParamMap.put("needUserInfo","1");
			newParamMap.put("idcNum","0");
			
			//获取会话信息
			Map<String,String> cookieMap  = cookiesMap.get(effectUid);
			
			String content = YhHttpUtil.sendHttpGetWithRetry(getPhotoInfoUrl(), newParamMap,cookieMap);
			Map<String, Object> contentMap = JSON.parseObject(content, Map.class);
			Object code = contentMap.get("code");
			Object subcode = contentMap.get("subcode");
			String codeStr = code != null ? code.toString() : "";
			String subcodeStr = subcode != null ? subcode.toString() : "";
			String message = ObjectUtil.toString(contentMap.get("message"));
			boolean checkFlag = checkOffen(codeStr, subcodeStr,message);
			contentMap.put("checkFlag", checkFlag);
			while( checkFlag && (photoUids.size()) > 0){
				System.out.println(content);
				photoUids.remove(effectUid);
				return getQzonePhotoInfo(uid);
			}
			return contentMap;
		}else{
			System.out.println("################### photoInfo is over ######################");
		}
		return null;
	}
	
	
	
public Map<String,Object> getQzoneBaseInfoContent(String uid){
		
		if(uids != null && uids.size() > 0 ){
			long systime = System.currentTimeMillis()%uids.size();
			int index = (int)systime;
			String effectUid = uids.get(index);
			//获取参数
			Map<String,Object> originMap = paramsMap.get(effectUid);
			Map<String,Object> newParamMap = new HashMap<String, Object>();
			newParamMap.put("g_tk", originMap.get("g_tk"));
			newParamMap.put("qzonetoken", originMap.get("qzonetoken"));
			newParamMap.put("format","json");
			newParamMap.put("uin",uid);
			newParamMap.put("vuin",effectUid);
			newParamMap.put("fupdate",1);
			
			//获取会话信息
			Map<String,String> cookieMap  = cookiesMap.get(effectUid);
			
			String content = YhHttpUtil.sendHttpGetWithRetry(getBaseInfoUrl(), newParamMap,cookieMap);
			try{
			Map<String, Object> contentMap = JSON.parseObject(content, Map.class);
			Object code = contentMap.get("code");
			Object subcode = contentMap.get("subcode");
			String codeStr = code != null ? code.toString() : "";
			String subcodeStr = subcode != null ? subcode.toString() : "";
			String message = ObjectUtil.toString(contentMap.get("message"));
			boolean checkFlag = checkOffen(codeStr, subcodeStr,message);
			contentMap.put("checkFlag", checkFlag);
			while( checkFlag && (uids.size()) > 0){
				System.out.println("【basinfo "+effectUid+"】="+content);
				//uids.remove(effectUid);
				removeUids(effectUid);
				return getQzoneBaseInfoContent(uid);
			}
			return contentMap;
			}catch(Exception e){
				Map<String,Object> eMap = new HashMap<String,Object>();
				eMap.put("gateWay",true);
				e.printStackTrace();
				System.out.println("basinfo------"+content);
				return eMap;
			}
		}else{
			System.out.println("################### baseInfo is over ######################");
		}
		return null;
	}


public Map<String,Object> getQzoneVisitInfoContent(String uid){
	
	if(visitUids != null && visitUids.size() > 0 ){
		long systime = System.currentTimeMillis()%visitUids.size();
		int index = (int)systime;
		String effectUid = visitUids.get(index);
		//获取参数
		Map<String,Object> originMap = paramsMap.get(effectUid);
		Map<String,Object> newParamMap = new HashMap<String, Object>();
		newParamMap.put("g_tk", originMap.get("g_tk"));
		newParamMap.put("qzonetoken", originMap.get("qzonetoken"));
		newParamMap.put("format","json");
		newParamMap.put("uin",uid);
		newParamMap.put("mask","2");
		newParamMap.put("mod","2");
		newParamMap.put("fupdate",1);
		
		//获取会话信息
		Map<String,String> cookieMap  = cookiesMap.get(effectUid);
		
		String content = YhHttpUtil.sendHttpGetWithRetry(getVisitUrl(), newParamMap,cookieMap);
		try{
		Map<String, Object> contentMap = JSON.parseObject(content, Map.class);
		Object code = contentMap.get("code");
		Object subcode = contentMap.get("subcode");
		String codeStr = code != null ? code.toString() : "";
		String subcodeStr = subcode != null ? subcode.toString() : "";
		String message = ObjectUtil.toString(contentMap.get("message"));
		boolean checkFlag = checkOffen(codeStr, subcodeStr,message);
		contentMap.put("checkFlag", checkFlag);
		while( checkFlag && (visitUids.size()) > 0){
			System.out.println("【"+effectUid+"】="+content);
			visitUids.remove(effectUid);
			//removeUids(effectUid);
			return getQzoneVisitInfoContent(uid);
		}
		return contentMap;
		}catch(Exception e){
			Map<String,Object> eMap = new HashMap<String,Object>();
			eMap.put("gateWay",true);
			e.printStackTrace();
			System.out.println("visitInfo------"+content);
			return eMap;
		}
	}else{
		System.out.println("################### visitinfo is over ######################");
	}
	return null;
}
	
	
	
	
	public boolean checkOffen(String codeStr,String subcodeStr,String message){
		return  ( 
					(
					QcodeEnum.QOFEN_P.code+"").equals(codeStr) &&
					subcodeStr.equals(QcodeEnum.QOFEN_P.subcode+"")
					) 
				|| 
				(
						codeStr.equals(QcodeEnum.QLOGIN_P.code + "") && 
						subcodeStr.equals(QcodeEnum.QLOGIN_P.subcode + ""
				)
						||
						(message.indexOf("频") >= 0)
				
				);
	}
	
	public void removeUids(String uid){
		this.uids.remove(uid);
		this.photoUids.remove(uid);
		this.emotUids.remove(uid);
		this.msgUids.remove(uid);
		
	}
	
	
	
	
	
	
	
	
	
	
	

}
