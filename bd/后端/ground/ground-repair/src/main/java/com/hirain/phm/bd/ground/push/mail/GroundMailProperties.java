/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.push.mail;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jul 7, 2020 2:50:09 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 7, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@Component
@ConfigurationProperties("ground.mail")
public class GroundMailProperties {

	private String from;

	private String urlPrefix;

	private String mobilePrefix;
}
