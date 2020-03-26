/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.param;

import java.util.List;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 18, 2019 11:31:32 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 18, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class AlgorithmStatisticsResponse {

	private int total;

	private int running;

	private int error;

	private int stopped;

	private List<SubsystemCount> subsystems;
}
