package com.man.erpcenter.sales.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.alibaba.fastjson.JSON;
import com.man.erpcenter.common.utils.ObjectUtil;

public class WebsocketEndPoint extends TextWebSocketHandler {

	private Map<Integer, WebSocketSession> sMap = new HashMap<Integer, WebSocketSession>();

	private static int BASEINFO = 1000;

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		super.handleTextMessage(session, message);
		String content = message.getPayload();
		Map<String, Object> cm = null;
		try {
			cm = JSON.parseObject(content, Map.class);
			int type = ObjectUtil.parseInt(cm.get("type"));
			if (type == BASEINFO) {
				sMap.put(type, session);
			}
		} catch (Exception e) {

		}
		// TextMessage returnMessage = new TextMessage(+" received at server
		// "+session.getId());
		// session.sendMessage(returnMessage);
	}
	
	public void sendMsg(Integer type,String json){
		WebSocketSession session = sMap.get(type);
		if(null != session && session.isOpen()){
			TextMessage returnMessage = new TextMessage(json);
			try {
				session.sendMessage(returnMessage);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getCookie(WebSocketSession session,String key){
		return null;
	}

}
