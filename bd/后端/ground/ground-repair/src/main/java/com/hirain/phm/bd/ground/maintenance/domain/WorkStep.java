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
import com.fasterxml.jackson.databind.JsonNode;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月29日 下午4:20:51
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月29日 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@Table(name = "t_step")
public class WorkStep implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "JDBC")
	private Long id;

	private Long sheetId;

	private Integer seq;

	private Date startTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endTime;

	private Long auditorId;

	@Transient
	private String auditor;

	private String result;

	private String content;

	private String type;

	@Transient
	private JsonNode data;

	public static WorkStep newStep(Long sheetId, String type) {
		WorkStep step = new WorkStep();
		step.setSheetId(sheetId);
		step.setStartTime(new Date());
		step.setType(type);
		return step;
	}
}
