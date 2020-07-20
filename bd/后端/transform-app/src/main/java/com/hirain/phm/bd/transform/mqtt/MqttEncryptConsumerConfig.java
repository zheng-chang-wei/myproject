/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.transform.mqtt;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import com.hirain.phm.bd.common.serialize.JsonUtil;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月15日 下午1:32:39
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
public class MqttEncryptConsumerConfig {

	@Autowired
	private MqttProperties properties;

	@Autowired
	private MqttSslProperties sslProperties;

	public MqttConnectOptions encryptedConnectOptions() {
		MqttConnectOptions options = new MqttConnectOptions();
		options.setServerURIs(new String[] { sslProperties.getUrl() });
		options.setKeepAliveInterval(properties.getKeepAlive());
		try {
			options.setSocketFactory(SslUtil.getSocketFactory(sslProperties.getCaFile(), sslProperties.getClientFile(), sslProperties.getKeyFile(),
					sslProperties.getPassword()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> will = new HashMap<>();
		will.put("sid", 0x18);
		will.put("on", false);
		String json = JsonUtil.toString(will);
		options.setWill("ground-train", json.getBytes(), 1, false);
		return options;
	}

	public MqttPahoClientFactory encryptedClientFactory() {
		DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
		factory.setConnectionOptions(encryptedConnectOptions());
		return factory;
	}

	@Bean("encryptedInputChannel")
	public MessageChannel mqttInputChannel() {
		return new DirectChannel();
	}

	@Bean("encryptedInbound")
	public MessageProducer inbound() {
		// Topic==train-ground
		MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(properties.getClientId() + "_inbound_encrypt",
				encryptedClientFactory(), sslProperties.getTopic());
		adapter.setCompletionTimeout(properties.getCompletionTimeout());
		adapter.setConverter(new DefaultPahoMessageConverter());
		adapter.setQos(properties.getQos());
		adapter.setOutputChannel(mqttInputChannel());
		return adapter;
	}

	@Bean
	@ServiceActivator(inputChannel = "encryptedInputChannel")
	public MessageHandler encryptedHandler() {
		return new EncryptedMessageHandler();
	}
}
