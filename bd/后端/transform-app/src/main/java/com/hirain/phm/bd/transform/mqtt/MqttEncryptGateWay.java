/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.transform.mqtt;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月15日 下午2:01:34
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月15日 jianwen.xin@hirain.com 1.0 create file
 */
@MessagingGateway(defaultRequestChannel = "encryptedOutChannel")
public interface MqttEncryptGateWay {

	void sendToMqtt(String data, @Header(MqttHeaders.TOPIC) String topic);
}
