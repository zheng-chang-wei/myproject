/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.service;

import java.util.Map;
import java.util.Queue;

import com.hirain.phm.synapsis.algorithm.domain.AlgorithmStatus;
import com.hirain.phm.synapsis.algorithm.message.AlgoRawDataMessage;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Dec 18, 2019 11:42:36 AM
 * @Description
 *              <p>  壳子和算法交互的service
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 18, 2019     zepei.tao@hirain.com    1.0   create file   
 */
public interface AlgoService {

	void init() throws Exception;

	/**
	 * 启动算法
	 */
	int launch();

	/**
	 * 停止算法
	 */
	int shut();

	/**
	 * 查询算法状态
	 */
	Map<Integer, AlgorithmStatus> statusInquire();

	/**
	 * 发送收到通信端口的回复报文
	 */
	void sendUdpPortResponse(int udpPort);

	/**
	 * 发送收到算法结果的回复报文
	 */
	void sendAlgoResultResponse(int udpPort);

	/**
	 * 发送算法Code
	 */
	//	AlgoCodeResponseMessage sendAlgoCode(int algoCode) throws Exception;

	/**
	 * 发送算法原始数据
	 */
	void sendAlgoRawData(AlgoRawDataMessage rawMessage);

	/**
	 * 获取存放算法code的队列
	 * @return
	 */
	Queue<Integer> getAlgoCodeQueue();

}
