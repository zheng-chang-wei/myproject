/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.digital.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Sep 25, 2019 10:47:05 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Sep 25, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Table(name = "t_digital_param")
@Data
public class DigitalTwinParam {

	@Id
	private Integer id;

	@Column(name = "param_key")
	private String key;

	private String name;

	private String unit;

	@Column(name = "param_type")
	private String type;
}
