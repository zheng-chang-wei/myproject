/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.transform.mqtt;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月15日 下午1:47:18
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月15日 jianwen.xin@hirain.com 1.0 create file
 */
@Configuration
@IntegrationComponentScan
public class MqttEncryptProducerConfig {

	@Autowired
	private MqttProperties properties;

	@Autowired
	private MqttSslProperties sslProperties;

	public MqttConnectOptions mqttConnectOptions() {
		MqttConnectOptions options = new MqttConnectOptions();
		options.setServerURIs(new String[] { sslProperties.getUrl() });
		options.setKeepAliveInterval(2);
		try {
			options.setSocketFactory(SslUtil.getSocketFactory(sslProperties.getCaFile(), sslProperties.getClientFile(), sslProperties.getKeyFile(),
					sslProperties.getPassword()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return options;
	}

	public MqttPahoClientFactory mqttClientFactory() {
		DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
		factory.setConnectionOptions(mqttConnectOptions());
		return factory;
	}

	@Bean
	@ServiceActivator(inputChannel = "encryptedOutChannel")
	public MessageHandler mqttOutbound1() {
		MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(properties.getClientId() + "_outbound_encrypt", mqttClientFactory());
		messageHandler.setAsync(true);
		return messageHandler;
	}

	@Bean("encryptedOutChannel")
	public MessageChannel mqttOutboundChannel() {
		return new DirectChannel();
	}
}
