/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.message.factory;

import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.algorithm.message.AlgoUdpPortMessage;
import com.hirain.phm.synapsis.algorithm.util.SidConstant;
import com.hirain.phm.synapsis.message.ResponseFactory;
import com.hirain.phm.synapsis.message.SynapsisResponse;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Dec 23, 2019 11:36:01 AM
 * @Description
 *              <p>解析算法发送过来的udp端口以及算法进程PID信息
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 23, 2019     zepei.tao@hirain.com    1.0   create file   
 */
@Component("PHM_ALGO" + SidConstant.PHM_UDPPORTPID_COMMAND)
public class AlgoUdpPortPIDFactory implements ResponseFactory {

	/** 
	 * @see com.hirain.phm.synapsis.message.ResponseFactory#create(byte[])
	 */
	@Override
	public SynapsisResponse create(byte[] datas) {
		AlgoUdpPortMessage message = new AlgoUdpPortMessage();
		message.parse(datas);
		return message;
	}

}
