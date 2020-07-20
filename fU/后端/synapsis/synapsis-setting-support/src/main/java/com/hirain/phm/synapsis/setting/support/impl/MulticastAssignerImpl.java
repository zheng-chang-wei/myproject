/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.setting.support.MulticastAssigner;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
@ConfigurationProperties("synapsis.multicast")
public class MulticastAssignerImpl implements MulticastAssigner {

	@Setter
	@Getter
	private Ip ip = new Ip();

	@Getter
	@Setter
	private int port;

	private long consumptionId;

	private int[] ipArray = new int[4];

	/**
	 * @see com.hirain.phm.synapsis.setting.support.MulticastAssigner#start()
	 */
	@Override
	public synchronized void start() {
		consumptionId = 0;
		for (int i = 0; i < ip.getStart().size(); i++) {
			ipArray[i] = Integer.parseInt(ip.getStart().get(i));
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
		ipArray[3]++;
		if (ipArray[3] > 255) {
			ipArray[3] = 1;
			ipArray[2]++;
		}
		if (ipArray[2] > 255) {
			ipArray[2] = 0;
			ipArray[1]++;
		}
		if (ipArray[1] > 255) {
			ipArray[1] = 0;
			ipArray[0]++;
		}
		return ipArray[0] + "." + ipArray[1] + "." + ipArray[2] + "." + ipArray[3];
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
		return ip.getStart().stream().collect(Collectors.joining("."));
	}

	@Data
	public static class Ip {

		private List<String> start;

		private List<String> end;
	}
}
