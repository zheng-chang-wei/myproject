/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2020年2月17日 上午10:36:24
 * @Description
 *              <p>
 *              运营影响表
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2020年2月17日 changwei.zheng@hirain.com 1.0 create file
 */
@Data
@Table(name = "t_effect")
public class Effect implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "JDBC")
	private Long id;

	/**
	 * 运营影响名称
	 */
	private String effectName;
}
