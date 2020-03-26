/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.subhealth.param;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Nov 18, 2019 6:50:00 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Nov 18, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SubhealthWithSuggestionParams extends SubhealthDetailParams {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String suggestion;

	private String treatment;

	private String repair;

	private String description;

	private String solution;

}
