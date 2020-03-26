/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.domain;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 20, 2020 4:17:08 PM
 * @Description
 *              <p>
 *              分析结果的公共包头
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 20, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@Table(name = "t_common_header")
public class CommonHeader {

	@Id
	private Long id;

	private Date systemTime;

	private Integer longiDegree;

	private Double longiMinute;

	private String longiDirection;

	private Integer latiDegree;

	private Double latiMinute;

	private String latiDirection;

	private String version;

	private Integer crc;
}
