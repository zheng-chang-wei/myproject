/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.comm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hirain.phm.synapsis.connection.Connection;
import com.hirain.phm.synapsis.udp.UDPServer;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 2, 2019 5:29:08 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 2, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Configuration
public class ConnectionConfiguration {

	/**
	 * 壳子和控制管理程序的通信端口
	 */
	@Value("${synapsis.local.control_port}")
	private int control_port;

	/**
	 * 壳子和算法程序的通信端口
	 */
	@Value("${synapsis.local.phm_port}")
	private int phm_port;

	@Bean("PHM_CONTROL")
	public Connection case2ControlConnection() {
		return new UDPServer(control_port);
	}

	@Bean("PHM_ALGO")
	public Connection case2AlgoConnection() {
		return new UDPServer(phm_port);
	}

}
