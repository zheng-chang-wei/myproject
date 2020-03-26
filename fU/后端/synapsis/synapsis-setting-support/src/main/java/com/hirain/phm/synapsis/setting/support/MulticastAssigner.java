/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 11, 2019 2:29:33 PM
 * @Description
 *              <p>
 *              组播地址分配
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 11, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface MulticastAssigner {

	/**
	 * 开始新一轮分配
	 */
	void start();

	/**
	 * 返回下一个消费者ID
	 * 
	 * @return
	 */
	long nextConsumption();

	/**
	 * 返回下一个组播Ip
	 * 
	 * @return
	 */
	String nextIp();

	/**
	 * 返回下一个组播端口
	 * 
	 * @return
	 */
	int nextPort();

	/**
	 * 返回重发IP
	 * 
	 * @return
	 */
	String retryIp();

}
