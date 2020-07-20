/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.websocket;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.hirain.phm.bd.common.serialize.JsonUtil;
import com.hirain.phm.bd.ground.realtime.domain.MonitorOption;
import com.hirain.phm.bd.ground.realtime.service.IOptionTextService;
import com.hirain.phm.bd.message.decode.DecodePacket;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月15日 上午10:02:14
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月15日 jianwen.xin@hirain.com 1.0 create file
 */
@Component
@ServerEndpoint(value = "/websocket")
@Slf4j
public class WebSocketConnection {

	static private IOptionTextService textService;

	private MonitorOption monitorOption;

	private Session session;

	@Autowired
	public void setTextService(IOptionTextService textService) {
		WebSocketConnection.textService = textService;
	}

	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		WebSocketServer.addConnection(textService.getKey(monitorOption), this);
		sendMessage(WebSocketServer.getFaultPacketsAsString());
	}

	@OnClose
	public void onClose() {
		log.info(monitorOption + ":websocket close");
		WebSocketServer.removeConnection(textService.getKey(monitorOption), this);
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		System.err.println("receive:" + message);
		MonitorOption newOption = null;
		try {
			newOption = JsonUtil.fromString(message, MonitorOption.class);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		} finally {
		}
		if (newOption.equals(monitorOption)) {
			return;
		}
		String key = textService.getKey(monitorOption);
		String newKey = textService.getKey(newOption);
		if (!key.equals(newKey)) {
			System.err.println(key + "   " + newKey);
			WebSocketServer.removeConnection(key, this);
			WebSocketServer.addConnection(newKey, this);
		}
		monitorOption = newOption;
	}

	@OnError
	public void onError(Session session, Throwable e) {
		System.out.println("error");
		e.printStackTrace();
	}

	public boolean follows(DecodePacket packet) {
		if (textService.getKey(packet).equals(textService.getKey(monitorOption))) {
			return true;
		} else {
			return false;
		}
	}

	@Async
	public void sendMessage(String message) {
		if (session.isOpen()) {
			// 非阻塞发送，需要后续观察，tomcat有bug，导致非阻塞发送并非线程安全
			synchronized (session) {
				session.getAsyncRemote().sendText(message);
			}
			// 阻塞发送
			// try {
			// session.getBasicRemote().sendText(message);
			// } catch (Exception e) {
			// log.error(e.getMessage(), e);
			// }
		}
	}
}
