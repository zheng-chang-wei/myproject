/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.fault.param;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jul 6, 2020 4:15:44 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 6, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class FaultCount {

	private String item;

	private Integer car;

	private Integer door;

	private Integer total;
}
