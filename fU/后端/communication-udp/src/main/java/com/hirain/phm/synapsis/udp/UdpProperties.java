/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.udp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

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
public class UdpProperties {

	/**
	 * 最大可接收字段长度
	 */
	@Value("${connection.udp.recvbyte:65535}")
	@Getter
	private int recvByteAllocate;

	@Value("${connection.udp.rvcbuf:1024}")
	@Getter
	private int rcvBuf;

	@Value("${connection.udp.reuseaddr:true}")
	@Getter
	private boolean reuseAddr;
}
