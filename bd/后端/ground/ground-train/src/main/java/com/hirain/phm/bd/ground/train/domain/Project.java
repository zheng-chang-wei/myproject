/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.train.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年11月8日 下午3:50:07
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年11月8日 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@Table(name = "t_project")
public class Project {

	@Id
	@GeneratedValue(generator = "JDBC")
	private Integer id;

	private String name;

	private String city;

	private String line;

	private String remarks;
}
