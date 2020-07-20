/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.domain;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 19, 2020 6:25:33 PM
 * @Description
 *              <p>
 *              分析结果
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 19, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@Table(name = "t_algorithm_result")
public class AlgorithmResult {

	@Transient
	public static int FILE_DATA = 0;

	@Transient
	public static int FILE_VIDEO = 1;

	@Id
	private Long id;

	private Integer code;

	private Integer fileType;

	private String name;

	private String algorithm;

	private String subsystem;

	@Transient
	private CommonHeader header;

	private Long headerId;

	@Transient
	private AlgorithmHeader algorithmHeader;

	private Long algorithmHeaderId;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date timestamp;

	private Integer value;
}
