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
 * @Created 2020年3月4日 下午5:22:59
 * @Description
 *              <p>
 *              维修履历工单配置变量表
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2020年3月4日 changwei.zheng@hirain.com 1.0 create file
 */
@Data
@Table(name = "t_config_variable")
public class ConfigVariable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "JDBC")
	private Long id;

	private Long userId;

	/**
	 * 变量名称
	 */
	private String name;
}
