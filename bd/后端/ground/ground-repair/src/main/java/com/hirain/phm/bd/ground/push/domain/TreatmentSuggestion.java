/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.push.domain;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Oct 18, 2019 9:43:58 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Oct 18, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@Table(name = "t_treatment_suggestion")
public class TreatmentSuggestion {

	@Id
	private Integer id;

	private String outline;

	private String suggestion;
}
