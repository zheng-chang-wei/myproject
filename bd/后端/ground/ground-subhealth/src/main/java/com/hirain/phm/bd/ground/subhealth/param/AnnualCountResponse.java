/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.subhealth.param;

import java.util.List;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Nov 15, 2019 3:10:26 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Nov 15, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class AnnualCountResponse {

	private int year;

	private List<Integer> counts;
}
