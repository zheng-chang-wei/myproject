/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.service;

import java.util.List;
import java.util.Map;

import com.hirain.phm.synapsis.algorithm.domain.RunStatus;
import com.hirain.phm.synapsis.algorithm.param.AlgorithmPacket;
import com.hirain.phm.synapsis.algorithm.param.AlgorithmStatisticsResponse;
import com.hirain.phm.synapsis.algorithm.param.Record;
import com.hirain.phm.synapsis.page.QueryRequest;
import com.hirain.phm.synapsis.response.PageResultBean;
import com.hirain.phm.synapsis.setting.AlgorithmSetting;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 17, 2019 9:30:18 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 17, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface AlgorithmService {

	PageResultBean<List<AlgorithmPacket>> list(QueryRequest request);

	/**
	 * 根据配置初始化算法对象
	 * 
	 * @param settings
	 */
	void init(List<AlgorithmSetting> settings);

	/**
	 * @param slotId
	 * @param statusMap
	 */
	void update(int slotId, Map<Integer, RunStatus> statusMap);

	/**
	 * 获得算法状态汇总信息
	 * 
	 * @return
	 */
	AlgorithmStatisticsResponse count();

	/**
	 * @param code
	 * @return
	 */
	AlgorithmSetting getSetting(int code);

	/**
	 * 算法运行历史记录
	 * 
	 * @return
	 */
	List<Record> getRecords();

	/**
	 * @param object
	 */
	void updateStatus(RunStatus status);
}
