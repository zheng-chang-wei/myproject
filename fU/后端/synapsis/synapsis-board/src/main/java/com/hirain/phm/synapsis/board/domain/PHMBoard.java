/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.board.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec 3, 2019 11:39:12 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 3, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PHMBoard extends Board {

	private String serverVer;

	private int ramSize;// 单位：MB

	private int ramUsed;// 百分比

	private int romSize;// 单位：MB

	private int romUsed;// 百分比

	private int cpuUsed;// 百分比

	private int gpuUsed;// 百分比
}
