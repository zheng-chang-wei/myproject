/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.rbac.domain;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 13, 2020 12:00:55 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 13, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@Table(name = "t_permission")
public class Permission {

	@Id
	private Integer id;

	private String permission;

	private String cname;
}
