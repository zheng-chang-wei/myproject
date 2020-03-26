/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.comm;

import java.nio.ByteBuffer;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.communication.Message;
import com.hirain.phm.synapsis.communication.impl.TransportMessage;
import com.hirain.phm.synapsis.message.MessageConstant;
import com.hirain.phm.synapsis.message.ResponseFactory;
import com.hirain.phm.synapsis.message.SynapsisRequest;
import com.hirain.phm.synapsis.message.SynapsisResponse;
import com.hirain.phm.synapsis.udp.codec.MessageCodec;
import com.hirain.phm.synapsis.udp.packet.UDPPacket;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 2, 2019 6:57:50 PM
 * @Description
 *              <p>
 *              编解码
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 2, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Component
public class ServiceMessageCodec implements MessageCodec {

	@Value("${synapsis.control.port}")
	private int controlPort;

	private String controlIp = "127.0.0.1";

	@Autowired
	private Map<String, ResponseFactory> factories;

	/**
	 * 设置目的IP和端口，报文对象转byte数组
	 * 
	 * @see com.hirain.phm.synapsis.udp.codec.MessageCodec#encode(com.hirain.phm.synapsis.communication.Message)
	 */
	@Override
	public UDPPacket encode(Message<?> message) {
		Object data = message.getData();
		UDPPacket packet = new UDPPacket();
		packet.setPort(controlPort);
		packet.setIp(controlIp);
		if (data instanceof SynapsisRequest) {
			SynapsisRequest request = (SynapsisRequest) data;
			packet.setSid(message.getSid());
			ByteBuffer buffer = ByteBuffer.allocate(12 + request.toBytes().length).order(MessageConstant.MESSAGE_ORDER);
			buffer.put((byte) message.getTarget());
			buffer.put((byte) message.getSource());
			buffer.putShort((short) message.getSid());
			buffer.putInt(0);// TODO 计数器
			buffer.putInt(request.toBytes().length);
			buffer.put(request.toBytes());
			packet.setData(buffer.array());
		}
		return packet;
	}

	/**
	 * byte数组转报文对象
	 * 
	 * @see com.hirain.phm.synapsis.udp.codec.MessageCodec#decode(com.hirain.phm.synapsis.udp.packet.UDPPacket)
	 */
	@Override
	public Message<?> decode(UDPPacket packet) {
		TransportMessage<SynapsisResponse> message = new TransportMessage<>();
		ByteBuffer buffer = ByteBuffer.wrap(packet.getData()).order(MessageConstant.MESSAGE_ORDER);
		message.setTarget(Byte.toUnsignedInt(buffer.get()));
		message.setSource(Byte.toUnsignedInt(buffer.get()));
		message.setSid(Short.toUnsignedInt(buffer.getShort()));
		message.setCounter(buffer.getInt());
		int length = buffer.getInt();
		byte[] datas = new byte[length];
		buffer.get(datas);
		ResponseFactory factory = factories.get(MessageConstant.Factory_PREFIX + packet.getSid());
		SynapsisResponse response = factory.create(datas);
		message.setData(response);
		return message;
	}

}
