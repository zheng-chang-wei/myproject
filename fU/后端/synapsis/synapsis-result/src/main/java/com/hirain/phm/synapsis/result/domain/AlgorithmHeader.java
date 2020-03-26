/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 20, 2020 6:19:34 PM
 * @Description
 *              <p>
 *              分析结果的算法包头
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 20, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@Table(name = "t_algorithm_header")
public class AlgorithmHeader {

	@Id
	private Long id;

	private Short headerType;

	private Integer algorithmId;

	private Integer supplier;

	private String version;

	private Integer lifeSignal;

	private Integer carriage;

	@Column(name = "terminal")
	private Integer end;

	private Integer deviceSeq;

	private Integer subsystem;

	private Integer crc;
}
