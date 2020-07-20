/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.train;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jul 7, 2020 3:03:28 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 7, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@ConfigurationProperties("mqtt.ssl")
@Component
public class MqttSslProperties {

	private String certFile;

	private String clientFile;

	private String keyFile;

	private String password;
}
