/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.dictionary.domain;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 15, 2020 10:18:13 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 15, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Table(name = "t_base_value")
@Data
public class BaseValue {

	@Id
	private Integer projectId;

	@Id
	private String variable;

	private String baseValues;
}
