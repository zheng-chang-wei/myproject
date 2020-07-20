/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jul 10, 2020 3:30:12 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 10, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Component
@ConfigurationProperties("synapsis.result")
@Data
public class ResultConfig {

	private String root;

	private String videoUrl;
}
