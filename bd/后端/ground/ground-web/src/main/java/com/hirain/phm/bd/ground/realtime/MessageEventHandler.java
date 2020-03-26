/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.realtime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import com.hirain.phm.bd.common.serialize.JsonUtil;
import com.hirain.phm.bd.ground.kafka.MessageEvent;
import com.hirain.phm.bd.ground.realtime.service.IDecoderService;
import com.hirain.phm.bd.ground.realtime.service.IOptionTextService;
import com.hirain.phm.bd.ground.websocket.WebSocketServer;
import com.hirain.phm.bd.ground.websocket.WebsocketPacket;
import com.hirain.phm.bd.message.decode.DecodePacket;
import com.hirain.phm.bd.message.decode.IDoorStateCalculator;
import com.hirain.phm.bd.message.decode.RunDataFrame;
import com.hirain.phm.bd.message.train.FaultPacket;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月13日 上午11:20:19
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月13日 jianwen.xin@hirain.com 1.0 create file
 */
@Configuration
public class MessageEventHandler {

	@Autowired
	private IDecoderService decodeService;

	@Autowired
	private IDoorStateCalculator calculator;

	@Autowired
	private IOptionTextService textService;

	@EventListener
	@Async
	public void onDoorPacket(MessageEvent event) {
		byte[] datas = event.getDatas();
		DecodePacket packet = decodeService.decode(datas);
		send(packet);
	}

	private void send(DecodePacket packet) {
		calculateState(packet);
		String message = JsonUtil.toString(new WebsocketPacket(packet));
		// System.out.println(message);
		WebSocketServer.sendMessage(textService.getKey(packet), message);
	}

	@EventListener
	@Async
	public void onFaultPacket(FaultPacket faultPacket) {
		WebSocketServer.addFaultPacket(faultPacket);
	}

	/**
	 * @param packet
	 */
	private void calculateState(DecodePacket packet) {
		for (RunDataFrame frame : packet.getFrames()) {
			frame.getValues().add(calculator.calculate(frame));
		}
		packet.getKeys().add("车门状态");
	}

}
