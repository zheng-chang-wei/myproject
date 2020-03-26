/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.communication;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 2, 2019 2:21:01 PM
 * @Description
 *              <p>
 *              通信模块接口
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 2, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface Communication {

	/**
	 * 同步发送
	 * 
	 * @param packet
	 * @param timeout
	 *            等待时间限制，单位秒
	 * @return
	 * @throws Exception
	 */
	Message<?> send(Message<?> packet, int timeout) throws Exception;

	/**
	 * 异步发送
	 * 
	 * @param packet
	 */
	void sendAsync(Message<?> packet);
}
