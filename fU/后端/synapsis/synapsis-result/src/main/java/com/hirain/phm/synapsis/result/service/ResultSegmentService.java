/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.service;

import com.hirain.phm.synapsis.result.domain.ResultSegmentContainer;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 21, 2020 9:36:53 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 21, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface ResultSegmentService {

	/**
	 * 获取分析结果中公共包头的字段列表
	 * 
	 * @return
	 */
	ResultSegmentContainer getHeaderSegments();

	/**
	 * 获取分析结果中算法部分的字段列表
	 * 
	 * @return
	 */
	ResultSegmentContainer getDataSegments();
}
