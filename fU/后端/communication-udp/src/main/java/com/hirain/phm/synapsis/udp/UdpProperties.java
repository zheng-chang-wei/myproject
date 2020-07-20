/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.udp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 10, 2020 9:22:10 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 10, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Component
@ConfigurationProperties("connection.udp")
@Data
public class UdpProperties {

	private int recvByteAllocate = 65535;

	private int rcvBuf = 1024;

	private boolean reuseAddr = true;
}
