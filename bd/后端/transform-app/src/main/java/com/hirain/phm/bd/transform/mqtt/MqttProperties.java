/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.transform.mqtt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月4日 上午11:48:51
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月4日 jianwen.xin@hirain.com 1.0 create file
 */
@Component
public class MqttProperties {

	@Value("${mqtt.url}")
	private String hostUrl;

	@Value("${mqtt.sslurl}")
	private String sslUrl;

	@Value("${mqtt.client.id}")
	private String clientId;

	@Value("${mqtt.completionTimeout}")
	private int completionTimeout; // 连接超时

	@Value("${mqtt.public.topic}")
	private String publicTopic;

	@Value("${mqtt.ssl.password}")
	private String sslPassword;

	@Value("${mqtt.ssl.file.ca}")
	private String caFile;

	@Value("${mqtt.ssl.file.client}")
	private String clientFile;

	@Value("${mqtt.ssl.file.key}")
	private String keyFile;

	@Value("${mqtt.ssl.topic}")
	private String encryptTopic;

	@Value("${mqtt.qos}")
	private int qos;

	@Value("${mqtt.keepalive}")
	private int keepAlive;

	/**
	 * @return the hostUrl
	 */
	public String getHostUrl() {
		return hostUrl;
	}

	/**
	 * @return the clientId
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * @return the completionTimeout
	 */
	public int getCompletionTimeout() {
		return completionTimeout;
	}

	/**
	 * @return the publicTopic
	 */
	public String getPublicTopic() {
		return publicTopic;
	}

	/**
	 * @return the sslPassword
	 */
	public String getSslPassword() {
		return sslPassword;
	}

	/**
	 * @return the caFile
	 */
	public String getCaFile() {
		return caFile;
	}

	/**
	 * @return the clientFile
	 */
	public String getClientFile() {
		return clientFile;
	}

	/**
	 * @return the keyFile
	 */
	public String getKeyFile() {
		return keyFile;
	}

	/**
	 * @return the sslUrl
	 */
	public String getSslUrl() {
		return sslUrl;
	}

	/**
	 * @return the encryptTopic
	 */
	public String getEncryptTopic() {
		return encryptTopic;
	}

	/**
	 * @return the qos
	 */
	public int getQos() {
		return qos;
	}

	public int getKeepAlive() {
		return keepAlive;
	}
}
