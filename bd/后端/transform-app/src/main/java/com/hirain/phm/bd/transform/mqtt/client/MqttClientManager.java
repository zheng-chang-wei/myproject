/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.transform.mqtt.client;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.net.SocketFactory;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hirain.phm.bd.transform.mqtt.MqttProperties;
import com.hirain.phm.bd.transform.mqtt.SslUtil;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月20日 下午1:35:30
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月20日 jianwen.xin@hirain.com 1.0 create file
 */
@Component
@Configuration
public class MqttClientManager {

	private Map<String, MqttMessageClient> clients;

	@Autowired
	private MqttProperties mqttProps;

	/**
	 * @param clientId
	 */
	public synchronized void createClient(String clientId, String realtimeTopic, String historyTopic) {
		if (clients == null) {
			clients = new ConcurrentHashMap<>();
		}
		if (!clients.containsKey(clientId)) {
			MqttMessageClient client = getClient();
			client.init(mqttProps.getHostUrl(), clientId, new String[] { realtimeTopic, historyTopic }, new int[] { 0, 1 });
			clients.put(clientId, client);
			client.start();
		}
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public MqttMessageClient getClient() {
		return new MqttMessageClient(messageConnectOptions());
	}

	@Bean
	public MqttConnectOptions messageConnectOptions() {
		MqttConnectOptions options = new MqttConnectOptions();
		options.setServerURIs(new String[] { mqttProps.getSslUrl() });
		options.setKeepAliveInterval(mqttProps.getKeepAlive());
		options.setCleanSession(false);
		options.setAutomaticReconnect(true);
		try {
			options.setSocketFactory(socketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return options;
	}

	@Bean
	public SocketFactory socketFactory() throws Exception {
		return SslUtil.getSocketFactory(mqttProps.getCaFile(), mqttProps.getClientFile(), mqttProps.getKeyFile(), mqttProps.getSslPassword());
	}
}
