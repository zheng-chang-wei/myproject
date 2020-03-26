/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.transform;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.hirain.phm.bd.transform.mqtt.SslUtil;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月15日 下午2:32:00
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月15日 jianwen.xin@hirain.com 1.0 create file
 */
public class MockTrainClient1 {

	public static void main(String[] args) {
		publicClient();

		encryptedClient();
	}

	/**
	 * 
	 */
	private static void encryptedClient() {
		try {
			MqttClient client = new MqttClient("ssl://10.40.2.34:8883", "mocktrain2");
			MqttConnectOptions options = new MqttConnectOptions();
			options.setKeepAliveInterval(2);
			options.setCleanSession(false);
			options.setSocketFactory(SslUtil.getSocketFactory("F:\\ssl\\ca.crt", "F:\\ssl\\client.crt", "F:\\ssl\\client.key", "123456"));

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

			client.subscribe("response/#");
		} catch (MqttException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	private static void publicClient() {
		try {
			MqttClient client = new MqttClient("tcp://10.40.2.34:1883", "mocktrain1");
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

			client.subscribe("ca/#");
		} catch (MqttException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
