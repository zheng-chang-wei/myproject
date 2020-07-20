/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.update.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jul 15, 2020 5:33:24 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 15, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@ConfigurationProperties("synapsis.version")
@Component
public class UpdateProperties {

	private String compressionSuffix;

	private String versionRepository;

	private String versionActiveRoot;
}
