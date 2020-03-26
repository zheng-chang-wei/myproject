/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.ecn.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hirain.phm.synapsis.setting.ECNVariable;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 19, 2020 2:35:55 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 19, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@Table(name = "t_common_segment")
public class CommonSegment {

	@Transient
	public static final int SOURCE_BUS = 1;

	@Transient
	public static final int SOURCE_SYSTEM = 0;

	@Id
	private Long id;

	private Long segmentSettingId;

	private Integer index;

	private String name;

	private String dataType;

	private Integer byteLen;

	private Integer type;

	private String property;

	@Transient
	private ECNVariable source;

	private Long variableId;

}
