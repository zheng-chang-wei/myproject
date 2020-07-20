/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.service;

import com.hirain.phm.synapsis.algorithm.domain.PHMAlgoResult;
import com.hirain.phm.synapsis.algorithm.message.AlgoResultResponseMessage;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec 14, 2019 4:23:38 PM
 * @Description
 *              <p>
 *              壳子和控制管理程序交互的service
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 14, 2019 zepei.tao@hirain.com 1.0 create file
 */
public interface AlgoManagerService {

	/**
	 * 向算法程序查询其状态
	 */
	void algoStatusInquire() throws Exception;

	/**
	 * 查询算法管理程序自身的版本号
	 */
	void versionInquire();

	/**
	 * 控制算法程序启动
	 */
	void algoStart();

	/**
	 * 控制算法程序停止
	 */
	void algoStop();

	/**
	 * 开始接收数据（包括算法原始数据、视频数据）
	 */
	void dataStart();

	/**
	 * 停止接收数据
	 */
	void dataStop();

	/**
	 * 向PHM卡控制管理程序发送算法结果
	 */
	AlgoResultResponseMessage algoResultSend(PHMAlgoResult result) throws Exception;
}
