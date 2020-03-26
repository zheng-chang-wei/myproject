/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.fault.param;

import java.util.List;

import com.hirain.phm.bd.ground.fault.domain.FaultData;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 27, 2019 9:47:16 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 27, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class FaultDataResponse {

	private Long id;

	private String state;

	/**
	 * 电机电压，电机电流，编码器值
	 */
	private List<String> keys;

	private List<FaultData> datas;

	private List<List<String>> baseValues;
}
