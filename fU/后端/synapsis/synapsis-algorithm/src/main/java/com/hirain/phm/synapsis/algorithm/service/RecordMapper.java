/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.service;

import java.util.List;

import com.hirain.phm.synapsis.algorithm.domain.Algorithm;
import com.hirain.phm.synapsis.algorithm.domain.RunStatus;
import com.hirain.phm.synapsis.algorithm.param.Record;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 23, 2019 2:57:09 PM
 * @Description
 *              <p>
 *              记录算法运行历史
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 23, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface RecordMapper {

	/**
	 * 
	 */
	List<Record> getRecords();

	void addRecord(Algorithm algorithm, RunStatus oldStatus, RunStatus newStatus);

	void addRecord(String message);
}
