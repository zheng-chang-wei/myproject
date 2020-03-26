/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.qsy.shaft.websocket;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.hirain.qsy.shaft.common.model.ResponseBo;

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

	public static void sendUserMessage(String key, ResponseBo message) {
		if (null == key) {
			return;
		}
		String[] userName_uuid = key.split("_");
		for (Entry<String, WebSocketConnection> entry : connectionMap.entrySet()) {
			String userName = entry.getKey();
			String[] split = userName.split("_");
			if (split[0].equals(userName_uuid[0]) && !split[1].equals(userName_uuid[1])) {
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

	public static void sendMessage(String userName, ResponseBo message) {
		for (Entry<String, WebSocketConnection> entry : connectionMap.entrySet()) {
			String userName_uuid = entry.getKey();
			String[] split = userName_uuid.split("_");
			if (split[0].equals(userName)) {
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
