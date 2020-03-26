/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
/**
 * @Version 1.0   
 * @Author weijia.kong@hirain.com
 * @Created Jun 27, 2019 10:59:29 AM
 * @Description
 * <p>
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>Jun 27, 2019     weijia.kong@hirain.com            1.0        create file 
 */
package com.hirain.phm.bd.ground.websocket;

import java.util.List;

import com.hirain.phm.bd.message.decode.DecodePacket;
import com.hirain.phm.bd.message.train.FaultPacket;

import lombok.Data;

@Data
public class WebsocketPacket {

	private DecodePacket packet;

	private List<FaultPacket> fault;

	public WebsocketPacket(DecodePacket decodePacket) {
		this.packet = decodePacket;
	}

	public WebsocketPacket(List<FaultPacket> faultPackets) {
		this.fault = faultPackets;
	}

}
