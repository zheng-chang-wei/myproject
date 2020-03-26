/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年7月16日 下午4:48:59
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年7月16日 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@Table(name = "t_external_fault")
public class ExternalFault {

	@Id
	@GeneratedValue(generator = "JDBC")
	private Long id;

	private String trainId;

	private String doorId;

	private String faultMode;

	private Date time;

	private Boolean statistics;

}
