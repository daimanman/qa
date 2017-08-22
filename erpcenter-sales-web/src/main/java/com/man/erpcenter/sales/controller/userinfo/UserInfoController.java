package com.man.erpcenter.sales.controller.userinfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.dxm.mqservice.mq.MsgSenderService;
import com.man.erpcenter.sales.biz.manager.QqInfoManager;
import com.man.erpcenter.sales.biz.manager.StartDoJob;
import com.man.erpcenter.sales.biz.manager.StartUserThread;
import com.man.erpcenter.sales.biz.util.ObjectUtil;
import com.man.erpcenter.sales.client.constant.MqMsgInfoEnum;
import com.man.erpcenter.sales.client.mqvo.QemotInfoMqVo;
import com.man.erpcenter.sales.client.mqvo.QinfoCookieMqVo;
import com.man.erpcenter.sales.controller.BaseController;

@Controller
@RequestMapping("/uinfo")
public class UserInfoController extends BaseController {
	
	@Autowired
	private QqInfoManager infoManager;
	
	@Autowired
	private MsgSenderService msgSenderService;
	
	public String UPATH="/home/";
	public String WPATH="E:\\";
	
	@RequestMapping("/start")
	public void startGetAll(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String num = request.getParameter("num");
		int n = 10;
		try{
		n = Integer.parseInt(num);
		}catch(Exception e){
			n = 10;
		}
		sendJson(response,infoManager.getAll(n));
		initQc();
		if(infoManager.uids.size() > 0){
			StartUserThread startThread = new StartUserThread();
			startThread.infoManager = infoManager;
			new Thread(startThread).start();
		}
	}
	
	@RequestMapping("/getSpec")
	public void startSpec(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String,Object> params = getReqParams(request);
		StartUserThread startThread = new StartUserThread();
		startThread.infoManager = infoManager;
		startThread.uids = ObjectUtil.castListObj(params.get("uids"));
		startThread.flag = 1;
		new Thread(startThread).start();
		sendJson(response, params);
	}
	
	@RequestMapping("/getMsg")
	public void getMsg(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String,Object> params = getReqParams(request);
		List uids = ObjectUtil.castListObj(params.get("uids"));
		if(null != uids && uids.size() > 0){
			for(Object uidObj:uids){
				String uid = ObjectUtil.toString(uidObj);
				QemotInfoMqVo mqVo = new QemotInfoMqVo();
				mqVo.uid = uid;
				msgSenderService.sendMessage(mqVo, MqMsgInfoEnum.ADD_MSG.topic, MqMsgInfoEnum.ADD_MSG.tags);
			}
		}
	}
	@RequestMapping("/getEmot")
	public void getEmot(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String,Object> params = getReqParams(request);
		List uids = ObjectUtil.castListObj(params.get("uids"));
		if(null != uids && uids.size() > 0){
			for(Object uidObj:uids){
				String uid = ObjectUtil.toString(uidObj);
				QemotInfoMqVo mqVo = new QemotInfoMqVo();
				mqVo.uid = uid;
				msgSenderService.sendMessage(mqVo, MqMsgInfoEnum.ADD_EMOT.topic, MqMsgInfoEnum.ADD_EMOT.tags);
			}
		}
	}
	@RequestMapping("/getImg")
	public void getImg(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String,Object> params = getReqParams(request);
		List uids = ObjectUtil.castListObj(params.get("uids"));
		if(null != uids && uids.size() > 0){
			for(Object uidObj:uids){
				String uid = ObjectUtil.toString(uidObj);
				QemotInfoMqVo mqVo = new QemotInfoMqVo();
				mqVo.uid = uid;
				msgSenderService.sendMessage(mqVo, MqMsgInfoEnum.ADD_PHOTO.topic, MqMsgInfoEnum.ADD_PHOTO.tags);
			}
		}
	}
	
	
	@RequestMapping("/getVisit")
	public void getVisit(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String,Object> params = getReqParams(request);
		List uids = ObjectUtil.castListObj(params.get("uids"));
		if(null != uids && uids.size() > 0){
			for(Object uidObj:uids){
				String uid = ObjectUtil.toString(uidObj);
				System.out.println("----getVisit----"+uid);
				QemotInfoMqVo mqVo = new QemotInfoMqVo();
				mqVo.uid = uid;
				msgSenderService.sendMessage(uid, MqMsgInfoEnum.ADD_VISIT_DB.topic, MqMsgInfoEnum.ADD_VISIT_DB.tags);
			}
		}
		sendJson(response, params);
	}
	
	@RequestMapping("/crawU")
	public void crawU(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String,Object> params = getReqParams(request);
		long startId = ObjectUtil.parseLong(params.get("startId"));
		long endId = ObjectUtil.parseLong(params.get("endId"));
		if(startId > 0 && endId > 0 && endId >= startId){
			StartUserThread startThread = new StartUserThread();
			startThread.infoManager = infoManager;
			startThread.flag = 2;
			startThread.startId = startId;
			startThread.endId = endId;
			new Thread(startThread).start();
		}
		sendJson(response, params);
	}
	
	@RequestMapping("/getQ")
	public void getQ(HttpServletRequest request,HttpServletResponse response) throws IOException, URISyntaxException{
		Map<String,Object> params = getReqParams(request);
		System.out.println(JSON.toJSONString(params));
		List headers = ObjectUtil.castListObj(params.get("requestHeaders"));
		String url = ObjectUtil.toString(params.get("url"));
		Map<String,String> cookie = parseHeaders(headers);
		Map<String,Object> paramsq =  parseParamsFromUri(url);
		String uid = ObjectUtil.toString(paramsq.get("uin"));
		
		infoManager.cookiesMap.put(uid, cookie);
		infoManager.paramsMap.put(uid,paramsq);
		infoManager.initUids.add(uid);
		
		//sendJson(response, params);
		
	}
	
	@RequestMapping("/showQc")
	public void initQc(HttpServletRequest request,HttpServletResponse response) throws IOException{
		sendJson(response,infoManager.initUids);
	}
	
	private void initQc(){
		infoManager.uids.clear();
		infoManager.msgUids.clear();
		infoManager.photoUids.clear();
		infoManager.emotUids.clear();
		infoManager.visitUids.clear();
		
		infoManager.uids.addAll(infoManager.initUids);
		infoManager.msgUids.addAll(infoManager.initUids);
		infoManager.photoUids.addAll(infoManager.initUids);
		infoManager.emotUids.addAll(infoManager.initUids);
		infoManager.visitUids.addAll(infoManager.initUids);
		
		QinfoCookieMqVo vo = new QinfoCookieMqVo();
		vo.cookiesMap = infoManager.cookiesMap;
		vo.paramsMap = infoManager.paramsMap;
		vo.uids = infoManager.uids;
		writeQCJSON(vo);
		
		StartDoJob.START_FLAG = 1;
		infoManager.startWebFlag = 1;
	}
	
	@RequestMapping("/initF")
	public void initFormFile(HttpServletRequest request,HttpServletResponse response) throws IOException{
		File file = new File(WPATH);
		if(!file.exists()){
			file = new File(UPATH);
		}
		try{
		QinfoCookieMqVo vo = JSON.parseObject(IOUtils.toString(new FileInputStream(new File(file.getAbsolutePath()+"QC.json"))), QinfoCookieMqVo.class);
		infoManager.initUids.addAll(vo.uids);
		infoManager.paramsMap = vo.paramsMap;
		infoManager.cookiesMap = vo.cookiesMap;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void writeQCJSON(QinfoCookieMqVo vo){
		if(null != vo ){
			try {
				File file = new File(WPATH);
				if(!file.exists()){
					file = new File(UPATH);
				}
				FileOutputStream fos = new FileOutputStream(new File(file.getAbsolutePath()+"QC.json"));
				IOUtils.write(JSON.toJSONString(vo), fos);
				IOUtils.closeQuietly(fos);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Map<String,Object> parseParamsFromUri(String url) throws URISyntaxException{
		Map<String, Object> mapparams = new HashMap<String, Object>();  
        List<NameValuePair> params = URLEncodedUtils.parse(new URI(url), "UTF-8");  
        for (NameValuePair param : params) {  
            mapparams.put(param.getName(), param.getValue());  
        }  
        return mapparams;
	}
	
	public Map<String,String> parseHeaders(List headers){
		Map<String,String> headersMap = new HashMap<String,String>();
		if(null != headers && headers.size() > 0){
			for(Object ho:headers){
				Map m = ObjectUtil.castMapObj(ho);
				headersMap.put(ObjectUtil.toString(m.get("name")),ObjectUtil.toString(m.get("value")));
			}
		}
		return headersMap;
	}
}
