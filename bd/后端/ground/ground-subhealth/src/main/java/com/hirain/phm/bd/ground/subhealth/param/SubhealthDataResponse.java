/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.subhealth.param;

import java.util.List;

import com.hirain.phm.bd.ground.subhealth.domain.SubhealthData;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 6, 2020 3:27:57 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 6, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class SubhealthDataResponse {

	private Integer id;

	private String state;

	private List<String> keys;

	private List<SubhealthData> datas;

	private List<List<String>> baseValues;
}
