/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月29日 下午3:54:58
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月29日 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@Table(name = "t_worksheet")
public class WorkSheet implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "JDBC")
	private Long id;

	private Long userId;

	@Transient
	private String user;

	private String state;

	private String processInstanceId;

	private Date createTime;

	private Long stepId;

	private Integer projectId;

	@Transient
	private String project;

	private Integer faultType;

	@Transient
	private String faultTypeName;

	/**
	 * 故障模式Id
	 */
	private Integer modeId;

	@Transient
	private String faultMode;

	private String trainId;

	private String doorId;

	private Long faultId;

	private Integer faultCode;

	private String faultName;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date faultTime;

	private Long detailId;

	@Transient
	private WorkDetail detail;

}
