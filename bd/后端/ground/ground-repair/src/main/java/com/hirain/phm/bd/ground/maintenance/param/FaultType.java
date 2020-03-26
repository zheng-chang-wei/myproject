/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.param;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2020年3月3日 下午12:07:11
 * @Description
 *              <p>
 *              Fault(0, "故障"),
 *              SubHealth(1, "亚健康预警"),
 *              Life(2, "寿命预警"),
 *              External(3, "外部故障");
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2020年3月3日 changwei.zheng@hirain.com 1.0 create file
 */
@Data
@AllArgsConstructor
public class FaultType {


	private int code;

	private String name;


}
