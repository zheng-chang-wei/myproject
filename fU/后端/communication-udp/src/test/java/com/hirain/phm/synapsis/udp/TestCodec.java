/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.udp;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.communication.Message;
import com.hirain.phm.synapsis.communication.impl.TransportMessage;
import com.hirain.phm.synapsis.udp.codec.MessageCodec;
import com.hirain.phm.synapsis.udp.packet.UDPPacket;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 4, 2019 11:33:04 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 4, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Component
public class TestCodec implements MessageCodec {

	@Value("${udp.test.ip}")
	private String targetIp;

	@Value("${udp.test.port}")
	private int targetPort;

	@Value("${udp.test.source.port}")
	private int sourcePort;

	/**
	 * @see com.hirain.phm.synapsis.udp.codec.MessageCodec#encode(com.hirain.phm.synapsis.communication.Message)
	 */
	@Override
	public UDPPacket encode(Message<?> packet) {
		UDPPacket message = new UDPPacket();
		message.setIp(targetIp);
		if (packet.getTarget() == 1) {
			message.setPort(targetPort);
			Object data = packet.getData();
			Request request = (Request) data;
			message.setSid(packet.getSid());
			ByteBuffer buffer = ByteBuffer.allocate(12 + 4).order(ByteOrder.LITTLE_ENDIAN);
			buffer.put((byte) packet.getTarget());
			buffer.put((byte) packet.getSource());
			buffer.putShort((short) packet.getSid());
			buffer.putInt(0);
			buffer.putInt(4);
			buffer.putInt(request.getMsg());
			message.setData(buffer.array());
		} else {
			message.setPort(sourcePort);
			Object data = packet.getData();
			Response response = (Response) data;
			message.setSid(packet.getSid());
			ByteBuffer buffer = ByteBuffer.allocate(12 + 4).order(ByteOrder.LITTLE_ENDIAN);
			buffer.put((byte) packet.getTarget());
			buffer.put((byte) packet.getSource());
			buffer.putShort((short) packet.getSid());
			buffer.putInt(0);
			buffer.putInt(4);
			buffer.putInt(response.getCode());
			message.setData(buffer.array());
		}
		return message;
	}

	/**
	 * @see com.hirain.phm.synapsis.udp.codec.MessageCodec#decode(com.hirain.phm.synapsis.udp.packet.UDPPacket)
	 */
	@Override
	public Message<?> decode(UDPPacket msg) {
		int target = getTarget(msg);
		int sid = msg.getSid();
		if (target == 2) {
			Response response = new Response();
			TransportMessage<Response> packet = new TransportMessage<>();
			packet.setSid(sid);
			ByteBuffer buffer = ByteBuffer.wrap(msg.getData()).order(ByteOrder.LITTLE_ENDIAN);
			packet.setTarget(buffer.get());
			packet.setSource(buffer.get());
			packet.setSid(buffer.getShort());
			packet.setCounter(buffer.getInt());
			buffer.getInt();
			response.setCode(buffer.getInt());
			packet.setData(response);
			return packet;
		} else if (target == 1) {
			Request request = new Request();
			TransportMessage<Request> packet = new TransportMessage<>();
			packet.setSid(sid);
			ByteBuffer buffer = ByteBuffer.wrap(msg.getData()).order(ByteOrder.LITTLE_ENDIAN);
			packet.setTarget(buffer.get());
			packet.setSource(buffer.get());
			packet.setSid(buffer.getShort());
			packet.setCounter(buffer.getInt());
			buffer.getInt();
			request.setMsg(buffer.getInt());
			packet.setData(request);
			return packet;
		}
		return null;
	}

	/**
	 * @param msg
	 * @return
	 */
	private int getTarget(UDPPacket msg) {
		return msg.getData()[0];
	}

}
