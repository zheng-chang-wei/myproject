/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.qsy.shaft.websocket;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import com.hirain.qsy.shaft.common.model.ResponseBo;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月15日 上午10:02:14
 * @Description
 *              <p>
 *              TODO WebSocketConnection的id
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月15日 jianwen.xin@hirain.com 1.0 create file
 */
@Component
@ServerEndpoint(value = "/websocket")
public class WebSocketConnection {

	private Session session;

	private String key;

	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
	}

	@OnClose
	public void onClose() {
		WebSocketServer.removeConnection(key, this);
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println("receive:" + message);
		this.key = message;
		WebSocketServer.sendUserMessage(this.key, ResponseBo.ok("已登录"));
		WebSocketServer.addConnection(this.key, this);
	}

	@OnError
	public void onError(Session session, Throwable e) {
		e.printStackTrace();
	}

	public synchronized void sendMessage(String message) throws IOException {
		if (session.isOpen()) {
			session.getBasicRemote().sendText(message);
		}
	}
}
