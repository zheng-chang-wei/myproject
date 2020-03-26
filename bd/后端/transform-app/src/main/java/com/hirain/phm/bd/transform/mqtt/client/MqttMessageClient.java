/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.transform.mqtt.client;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月8日 上午11:52:05
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月8日 jianwen.xin@hirain.com 1.0 create file
 */
@Slf4j
public class MqttMessageClient implements MqttCallbackExtended {

	private static final int DEFAULT_RECOVERY_INTERVAL = 10000;

	@Autowired
	private DoorMessageHandler callback;

	private MqttConnectOptions options;

	private MqttClient client;

	private String clientId;

	private String host;

	private ScheduledFuture<?> reconnectFuture;

	private int recoveryInterval = DEFAULT_RECOVERY_INTERVAL;

	private volatile boolean connected;

	private String[] topics;

	private TaskScheduler taskScheduler;

	private int[] qos;

	public MqttMessageClient(MqttConnectOptions options) {
		this.options = options;
	}

	public void init(String host, String clientId, String... topics) {
		this.host = host;
		this.clientId = clientId;
		this.topics = topics;
	}

	public void init(String host, String clientId, String[] topics, int[] qos) {
		init(host, clientId, topics);
		this.qos = qos;
	}

	public void start() {
		try {
			connectAndSubscribe();
		} catch (MqttException e) {
			log.error(e.getMessage(), e);
			scheduleReconnect();
		}
		log.info("{} start", clientId);
	}

	public void stop() {
		cancelReconnect();
		if (client != null) {
			try {
				client.disconnectForcibly(options.getConnectionTimeout());
			} catch (MqttException e) {
				log.error(e.getMessage(), e);
			}
			client.setCallback(null);
			try {
				client.close();
			} catch (MqttException e) {
				log.error(e.getMessage(), e);
			}
			connected = false;
			client = null;
		}
	}

	/**
	 * @throws MqttException
	 */
	private synchronized void connectAndSubscribe() throws MqttException {
		client = new MqttClient(host, clientId, new MemoryPersistence());
		try {
			client.setCallback(this);
			client.connect(options);
			client.subscribe(topics, getQos());
		} catch (MqttException e) {
			log.error(e.getMessage(), e);
			client.disconnectForcibly(options.getConnectionTimeout());
			try {
				client.setCallback(null);
				client.close();
			} catch (MqttException e1) {
			}
			client = null;
			throw e;
		}
		if (client.isConnected()) {
			connected = true;
		}
	}

	/**
	 * 
	 */
	private synchronized void scheduleReconnect() {
		cancelReconnect();
		try {
			reconnectFuture = getTaskScheduler().schedule((Runnable) () -> {
				try {
					synchronized (MqttMessageClient.this) {
						if (!MqttMessageClient.this.connected) {
							connectAndSubscribe();
							MqttMessageClient.this.reconnectFuture = null;
						}
					}
				} catch (MqttException e) {
					log.error(e.getMessage(), e);
					scheduleReconnect();
				}
			}, new Date(System.currentTimeMillis() + recoveryInterval));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private synchronized void cancelReconnect() {
		if (reconnectFuture != null) {
			reconnectFuture.cancel(false);
			reconnectFuture = null;
		}
	}

	private TaskScheduler getTaskScheduler() {
		if (taskScheduler == null) {
			taskScheduler = new ThreadPoolTaskScheduler();
			((ThreadPoolTaskScheduler) taskScheduler).initialize();
		}
		return taskScheduler;
	}

	private int[] getQos() {
		if (qos != null) {
			return qos;
		}
		int[] qos = new int[topics.length];
		for (int i = 0; i < topics.length; i++) {
			qos[i] = 1;
		}
		return qos;
	}

	/**
	 * @see org.eclipse.paho.client.mqttv3.MqttCallback#connectionLost(java.lang.Throwable)
	 */
	@Override
	public void connectionLost(Throwable cause) {
		log.error("Lost connection:" + cause.getMessage() + "; retrying...", cause);
		cause.printStackTrace();
		connected = false;
		if (client != null) {
			try {
				client.setCallback(null);
				client.close();
			} catch (Exception e) {
			}
		}
		client = null;
		scheduleReconnect();
	}

	/**
	 * @see org.eclipse.paho.client.mqttv3.MqttCallback#messageArrived(java.lang.String, org.eclipse.paho.client.mqttv3.MqttMessage)
	 */
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		callback.messageArrived(topic, message);
	}

	/**
	 * @see org.eclipse.paho.client.mqttv3.MqttCallback#deliveryComplete(org.eclipse.paho.client.mqttv3.IMqttDeliveryToken)
	 */
	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {

	}

	/**
	 * @see org.eclipse.paho.client.mqttv3.MqttCallbackExtended#connectComplete(boolean, java.lang.String)
	 */
	@Override
	public void connectComplete(boolean reconnect, String serverURI) {
	}
}
