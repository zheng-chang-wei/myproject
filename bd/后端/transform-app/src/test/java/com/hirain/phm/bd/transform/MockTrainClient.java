/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.transform;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.hirain.phm.bd.common.serialize.JsonUtil;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月9日 下午2:42:59
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月9日 jianwen.xin@hirain.com 1.0 create file
 */
public class MockTrainClient {

	public static void main(String[] args) throws InterruptedException {
		publicClient();
	}

	/**
	 * @throws InterruptedException
	 */
	private static void publicClient() throws InterruptedException {
		try {
			MqttClient client = new MqttClient("tcp://10.40.2.34:1883", "mock train", new MemoryPersistence());
			MqttConnectOptions options = new MqttConnectOptions();
			options.setKeepAliveInterval(2);
			options.setCleanSession(false);
			client.setCallback(new MqttCallback() {

				@Override
				public void messageArrived(String topic, MqttMessage message) throws Exception {
					System.out.println("topic:" + topic + ":message" + new String(message.getPayload()));
				}

				@Override
				public void deliveryComplete(IMqttDeliveryToken token) {
				}

				@Override
				public void connectionLost(Throwable cause) {
				}
			});
			client.connect(options);

			client.subscribe("train/#");
			String message = getMessage();
			try {
				client.publish("train/register", message.getBytes("utf-8"), 1, false);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			TimeUnit.SECONDS.sleep(5);
			client.disconnect();
			client.close();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	public static String getMessage() {
		Map<String, Object> map = new HashMap<>();
		map.put("city", "sh");
		map.put("line", "s1");
		map.put("train", "222");
		map.put("sid", 0x11);
		String json = JsonUtil.toString(map);
		return json;
	}
}
