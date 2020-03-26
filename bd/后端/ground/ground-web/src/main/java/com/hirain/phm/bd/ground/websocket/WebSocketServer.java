/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.websocket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.hirain.phm.bd.common.serialize.JsonUtil;
import com.hirain.phm.bd.message.train.FaultPacket;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月15日 上午10:13:40
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月15日 jianwen.xin@hirain.com 1.0 create file
 */
@Component
public class WebSocketServer {

	private static Map<String, List<WebSocketConnection>> connectionMap = new ConcurrentHashMap<>();

	private static AtomicInteger onlineCount = new AtomicInteger(0);

	private static LinkedList<FaultPacket> faultPackets = new LinkedList<>();

	private static int faultBufferSize = 7;

	public static void addConnection(String key, WebSocketConnection connection) {
		List<WebSocketConnection> connectionList;
		connectionList = connectionMap.get(key);
		if (connectionList == null) {
			connectionList = new ArrayList<>();
			connectionMap.put(key, connectionList);
		}
		synchronized (connectionList) {
			connectionList.add(connection);
		}
		onlineCount.incrementAndGet();
	}

	public static void removeConnection(String key, WebSocketConnection connection) {
		List<WebSocketConnection> connectionList = connectionMap.get(key);
		if (connectionList != null) {
			synchronized (connectionList) {
				connectionList.remove(connection);
			}
		}
		onlineCount.decrementAndGet();
	}

	public static void sendMessage(String key, String message) {
		if (null == key) {
			return;
		}
		List<WebSocketConnection> connectionList = connectionMap.get(key);
		if (connectionList == null) {
			return;
		}
		synchronized (connectionList) {
			for (WebSocketConnection connection : connectionList) {
				connection.sendMessage(message);
			}
		}
	}

	public static void addFaultPacket(FaultPacket faultPacket) {
		arrangeFaultBuffer();
		faultPackets.addFirst(faultPacket);
		sendFaultPackets();
	}

	@Async
	public static void sendFaultPackets() {
		// String faultString = getFaultPacketsAsString();
		Map<String, Object> map = new HashMap<>();
		map.put("fault", faultPackets);
		String message = JsonUtil.toString(map);
		for (List<WebSocketConnection> connectionList : connectionMap.values()) {
			synchronized (connectionList) {
				for (WebSocketConnection connection : connectionList) {
					connection.sendMessage(message);
				}
			}
		}
	}

	public static String getFaultPacketsAsString() {
		return JsonUtil.toString(new WebsocketPacket(faultPackets));
	}

	/**
	 * @return
	 * @deprecated by jianwen.xin Sep 17 2019,没有被调用？
	 */
	@Deprecated
	@SuppressWarnings("unused")
	private static int getTotalMessageNum() {
		int totalMessageNumber = 0;
		for (FaultPacket packet : faultPackets) {
			totalMessageNumber += packet.getMessages().size();
		}
		return totalMessageNumber;
	}

	private static void arrangeFaultBuffer() {
		int sumMessageNumber = 0;
		int i = faultPackets.size() - 1;
		for (; i >= 0; i--) {
			sumMessageNumber += faultPackets.get(i).getMessages().size();
			if (sumMessageNumber > faultBufferSize) {
				break;
			}
		}
		for (; i >= 0; i--) {
			faultPackets.removeLast();
		}
	}

}
