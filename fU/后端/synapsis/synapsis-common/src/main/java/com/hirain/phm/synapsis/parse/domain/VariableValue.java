/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.parse.domain;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 15, 2020 1:34:30 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 15, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class VariableValue {

	private int frameIndex;

	private LocalDateTime timestamp;

	private double value;
}
