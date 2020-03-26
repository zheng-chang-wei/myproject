/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.setting.support.MulticastAssigner;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 11, 2019 2:30:05 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 11, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Component
public class MulticastAssignerImpl implements MulticastAssigner {

	@Value("#{'${synapsis.multicast.ip.start}'.split('\\.')}")
	private List<String> startIp;

	@Value("#{'${synapsis.multicast.ip.end}'.split('\\.')}")
	private List<String> endIp;

	@Value("${synapsis.multicast.port}")
	private int port;

	private long consumptionId;

	private int[] ip = new int[4];

	/**
	 * @see com.hirain.phm.synapsis.setting.support.MulticastAssigner#start()
	 */
	@Override
	public synchronized void start() {
		consumptionId = 0;
		for (int i = 0; i < startIp.size(); i++) {
			ip[i] = Integer.parseInt(startIp.get(i));
		}
	}

	/**
	 * @return
	 */
	@Override
	public synchronized long nextConsumption() {
		return ++consumptionId;
	}

	/**
	 * 
	 */
	@Override
	public String nextIp() {
		ip[3]++;
		if (ip[3] > 255) {
			ip[3] = 1;
			ip[2]++;
		}
		if (ip[2] > 255) {
			ip[2] = 0;
			ip[1]++;
		}
		if (ip[1] > 255) {
			ip[1] = 0;
			ip[0]++;
		}
		return ip[0] + "." + ip[1] + "." + ip[2] + "." + ip[3];
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.support.MulticastAssigner#nextPort()
	 */
	@Override
	public int nextPort() {
		return port;
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.support.MulticastAssigner#retryIp()
	 */
	@Override
	public String retryIp() {
		return startIp.stream().collect(Collectors.joining("."));
	}
}
