/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.runtime.param;

import lombok.Data;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2020年2月24日 下午7:01:28
 * @Description
 *              <p>
 *              算法分组条目
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2020年2月24日 changwei.zheng@hirain.com 1.0 create file
 */
@Data
public class SubsystemGroupItem {

	private Integer id;

	private String name;

	private String description;

	private Boolean isUsed = true;

	private Integer count;

}
