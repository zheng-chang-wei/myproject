/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.domain;

import lombok.Data;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec 14, 2019 3:31:04 PM
 * @Description
 *              <p>
 *              从算法发送给算法壳的算法结果中解析出来的对象
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 14, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Data
public class PHMAlgoResult {

	private int algoId;

	private byte[] timestamp;

	/**
	 * 格式 "yyyyMMdd_HHmmss"
	 */
	private String dateTime;

	private byte[] data;

	/**
	 * 是否需要缓存数据
	 */
	private boolean saveData;

	/**
	 * 项点名称
	 */
	private String itemName;

}
