/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.decode.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jul 2, 2020 6:44:41 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 2, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@Component
@ConfigurationProperties("decode")
public class DecodeProperties {

	private int processorSize;
}
