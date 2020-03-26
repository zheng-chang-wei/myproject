/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.ecn.vo;

import com.hirain.phm.synapsis.setting.support.param.ECNVariableVO;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 19, 2020 2:52:24 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 19, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class CommonSegmentVO {

	private Integer index;

	private String name;

	private String dataType;

	private Integer byteLen;

	private Integer type;

	private String property;

	private ECNVariableVO source;

}
