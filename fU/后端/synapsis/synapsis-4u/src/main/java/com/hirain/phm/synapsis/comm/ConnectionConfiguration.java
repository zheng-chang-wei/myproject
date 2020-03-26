/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.comm;

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

	@Value("${synapsis.local.port}")
	private int port;

	@Bean("CPU_CONTROL")
	public Connection udpConnection() {
		return new UDPServer(port);
	}
}
