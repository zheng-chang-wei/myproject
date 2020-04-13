/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.ptu.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;


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
