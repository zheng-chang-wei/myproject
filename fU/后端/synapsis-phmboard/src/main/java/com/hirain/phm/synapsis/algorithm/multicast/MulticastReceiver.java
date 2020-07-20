/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.multicast;

import com.hirain.phm.synapsis.algorithm.message.MulticastAlgoMessage;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Dec 23, 2019 4:21:29 PM
 * @Description
 *              <p>
 *              组播接收者
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 23, 2019 zepei.tao@hirain.com 1.0 create file
 */
public interface MulticastReceiver {

	/**
	 * 开始监听
	 */
	void subscribe() throws Exception;

	/**
	 * 停止监听
	 */
	void stop() throws Exception;

	/**
	 * 将从组播地址接收到的UDP报文解析成算法订阅数据对象
	 */
	MulticastAlgoMessage decode(byte[] multicastDatas);

}
