/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.udp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hirain.phm.synapsis.connection.Connection;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 4, 2019 11:29:58 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 4, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Configuration
public class TestConfiguration {

	@Value("${udp.test.port}")
	private int targetPort;

	@Value("${udp.test.source.port}")
	private int sourcePort;

	@Bean("Service")
	public Connection connection() {
		return new UDPServer(sourcePort);
	}

	@Bean("Tester")
	public Connection testConnection() {
		return new UDPServer(targetPort);
	}

	@Bean("Send")
	public Connection sendConnection() {
		return new UDPServer(15525);
	}

}
