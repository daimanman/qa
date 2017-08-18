package com.man.erpcenter.sales.controller.userinfo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dxm.mqservice.mq.MsgSenderService;
import com.man.erpcenter.sales.biz.manager.QqInfoManager;
import com.man.erpcenter.sales.biz.manager.StartUserThread;
import com.man.erpcenter.sales.biz.util.ObjectUtil;
import com.man.erpcenter.sales.client.constant.MqMsgInfoEnum;
import com.man.erpcenter.sales.client.mqvo.QemotInfoMqVo;
import com.man.erpcenter.sales.controller.BaseController;

@Controller
@RequestMapping("/uinfo")
public class UserInfoController extends BaseController {
	
	@Autowired
	private QqInfoManager infoManager;
	
	@Autowired
	private MsgSenderService msgSenderService;
	
	@RequestMapping("/startGetAll")
	public void startGetAll(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String num = request.getParameter("num");
		int n = 10;
		try{
		n = Integer.parseInt(num);
		}catch(Exception e){
			n = 10;
		}
		sendJson(response,infoManager.getAll(n));
		StartUserThread startThread = new StartUserThread();
		startThread.infoManager = infoManager;
		new Thread(startThread).start();
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
}
