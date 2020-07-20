/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jul 10, 2020 3:25:30 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 10, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@ConfigurationProperties("synapsis.algorithm")
@Component
public class AlgorithmConfig {

	private int timeout = 10;

	private int statusPeriod = 10000;
}
