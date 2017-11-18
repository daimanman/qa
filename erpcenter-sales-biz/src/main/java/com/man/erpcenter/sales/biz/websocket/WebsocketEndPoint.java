package com.man.erpcenter.sales.biz.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.man.erpcenter.sales.biz.manager.RrManager;

public class WebsocketEndPoint extends TextWebSocketHandler {

	private Map<String, WebSocketSession> sMap = new HashMap<String, WebSocketSession>();

	private static String RR_USER_TOKEN_BASE="RR_USER_TOKEN_BASE";
	public static String RR_USER_BASE="1000";
	
	@Autowired
	private RrManager rrManager;

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		super.handleTextMessage(session, message);
		Object domain =  session.getHandshakeAttributes().get("domain");
		if(RR_USER_BASE.equals(domain)){
			sMap.put(domain.toString(),session);
			String content = message.getPayload();
			//基本信息
			rrManager.addRrUserBatch(content);
		}
		
	}
	
	public WebSocketSession sendMsg(String type,String content){
		WebSocketSession session = sMap.get(type);
		if(null != session && session.isOpen()){
			TextMessage returnMessage = new TextMessage(content);
			try {
				session.sendMessage(returnMessage);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return session;
	}
	
	private String getCookie(WebSocketSession session,String key){
		if(null != session){
			List<String> headers = session.getHandshakeHeaders().get("Cookie");
			for(String h:headers){
				String[] hs = h.split("=");
				if(hs.length > 0 && hs[0].toLowerCase().equals(key.toLowerCase().trim())){
					if(hs.length > 1){
						return hs[1];
					}
				}
			}
		}
		
		return null;
	}

}
