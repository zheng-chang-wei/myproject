/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.ptu.websocket;

import com.alibaba.fastjson.JSONObject;
import com.hirain.ptu.common.model.ResponseBo;
import com.hirain.ptu.common.model.WebSocketResponse;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class WebSocketServer {

	private static Map<String, WebSocketConnection> connectionMap = new ConcurrentHashMap<>();

	private static AtomicInteger onlineCount = new AtomicInteger(0);

	public static void addConnection(String key, WebSocketConnection connection) {
		connectionMap.put(key, connection);
		onlineCount.incrementAndGet();
	}

	public static void removeConnection(String key, WebSocketConnection connection) {
		connectionMap.remove(key);
		onlineCount.decrementAndGet();
	}

	public static void sendMessage(String key, WebSocketResponse message) {
		if (null == key) {
			return;
		}
		for (Entry<String, WebSocketConnection> entry : connectionMap.entrySet()) {
			String userName = entry.getKey();
			if (key.equals(userName)) {
				WebSocketConnection connection = entry.getValue();
				if (connection != null) {
					try {
						connection.sendMessage(JSONObject.toJSONString(message));
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				}
			}
		}
	}
}
