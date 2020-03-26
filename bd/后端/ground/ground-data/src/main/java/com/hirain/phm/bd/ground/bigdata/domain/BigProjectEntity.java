/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.bigdata.domain;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年11月11日 上午10:15:25
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年11月11日 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@Table(name = "t_big_project")
public class BigProjectEntity {

	@Id
	private String project;

	private Long used;
}
