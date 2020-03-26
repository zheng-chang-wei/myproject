/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.service;

import java.util.List;

import com.hirain.phm.synapsis.parse.domain.FileHeader;
import com.hirain.phm.synapsis.parse.domain.VariableData;
import com.hirain.phm.synapsis.result.domain.AlgorithmResult;
import com.hirain.phm.synapsis.setting.VariableGroup;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Feb 11, 2020 2:13:08 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 11, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface ResultDataLoader {

	/**
	 * 获取数据头信息，即文件包含的变量信息
	 * 
	 * @param result
	 * @return
	 * @throws Exception
	 */
	FileHeader getFileHeader(AlgorithmResult result) throws Exception;

	/**
	 * 查询所选变量的值
	 * 
	 * @param result
	 * @param selected
	 * @return
	 * @throws Exception
	 */
	List<VariableData> getVariableDatas(AlgorithmResult result, List<VariableGroup> selected) throws Exception;

	/**
	 * 获取视频信息
	 * 
	 * @param result
	 */
	String getVideo(AlgorithmResult result);
}
