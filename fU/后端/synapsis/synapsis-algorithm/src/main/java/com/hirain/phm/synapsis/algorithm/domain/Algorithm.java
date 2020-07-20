/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.domain;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 16, 2019 11:42:20 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 16, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class Algorithm {

	private int code;

	private String name;

	private String subsystem;

	private int slotId;

	private String board;

	private RunStatus status;

	private boolean enable;
}
