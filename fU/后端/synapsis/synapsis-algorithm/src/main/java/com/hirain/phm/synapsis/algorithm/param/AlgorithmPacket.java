/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.param;

import java.util.Map;

import com.hirain.phm.synapsis.algorithm.domain.Algorithm;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 23, 2019 1:35:08 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 23, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class AlgorithmPacket {

	private Algorithm data;

	private Map<String, Integer> variable;
}
