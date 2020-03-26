/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
/**
 * @Version 1.0   
 * @Author weijia.kong@hirain.com
 * @Created May 20, 2019 6:51:44 PM
 * @Description
 * <p>
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>May 20, 2019     weijia.kong@hirain.com            1.0        create file 
 */
package com.hirain.phm.bd.ground.realtime.service;

import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.realtime.domain.MonitorOption;
import com.hirain.phm.bd.message.decode.DecodePacket;

@Service
public class PacketToolService implements IOptionTextService {

	/*
	 * (non-Javadoc)
	 * @see com.hirain.phm.bd.ground.realtime.service.IOptionTextService#getKey(com.hirain.phm.bd.message.decode.DecodePacket)
	 */
	@Override
	public String getKey(DecodePacket packet) {
		if (packet == null) {
			return catString(null, null, null);
		} else {
			return catString(packet.getProject(), packet.getTrain());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.hirain.phm.bd.ground.realtime.service.IOptionTextService#getKey(com.hirain.phm.bd.ground.realtime.domain.MonitorOption)
	 */
	@Override
	public String getKey(MonitorOption option) {
		if (option == null) {
			return catString(null, null);
		} else {
			return catString(option.getProject(), option.getTrain());
		}
	}

	private String getString(String value) {
		if (null == value || value.isEmpty()) {
			return "null";
		}
		return value;
	}

	private String catString(String... strArray) {
		int n = strArray.length;
		String ans = getString(strArray[0]);
		for (int i = 1; i < n; i++) {
			ans += "_" + getString(strArray[i]);
		}
		return ans;
	}
}
