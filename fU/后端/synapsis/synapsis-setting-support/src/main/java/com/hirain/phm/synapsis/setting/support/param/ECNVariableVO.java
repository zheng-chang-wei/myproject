/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support.param;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec 9, 2019 10:58:38 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 9, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ECNVariableVO extends VariableVO {

	/**
	 * 源地址
	 */
	private String sourceIp;

	private Long comId;

	private Integer dataType;

	private Integer byteOffset;

	private Integer bitOffset;

	private Integer bitLen;

	/**
	 * 变量名称
	 */
	private String name;

	/**
	 * 单位
	 */
	private String unit;

	/**
	 * 变量别名
	 */
	private String signalName;
}
