/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.fault.param;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Nov 18, 2019 6:07:22 PM
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
public class FaultDetailWithSuggestionParams extends FaultDetailCommonParams {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 故障详情id
	 */
	private Long id;

	/**
	 * 故障发生时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date faultTime;

	private String suggestion;

	private String treatment;

	private String repair;

	private String description;

	private String solution;

}
