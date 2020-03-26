/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.board;

import java.nio.ByteBuffer;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.hirain.phm.synapsis.board.message.BoardControlResponseMessage;
import com.hirain.phm.synapsis.board.message.BoardLaunchMessage;
import com.hirain.phm.synapsis.board.message.BoardStatusInquireMessage;
import com.hirain.phm.synapsis.board.message.BoardStatusResponseMessage;
import com.hirain.phm.synapsis.communication.Message;
import com.hirain.phm.synapsis.communication.impl.TransportMessage;
import com.hirain.phm.synapsis.constant.SidConstant;
import com.hirain.phm.synapsis.message.MessageConstant;
import com.hirain.phm.synapsis.message.ResponseFactory;
import com.hirain.phm.synapsis.message.SynapsisRequest;
import com.hirain.phm.synapsis.message.SynapsisResponse;
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
// @Component
// @Profile("test")
public class TestCodec implements MessageCodec {

	@Value("${synapsis.control.port}")
	private int targetPort;

	@Value("${synapsis.local.port}")
	private int sourcePort;

	@Value("${udp.test.ip:127.0.0.1}")
	private String ip;

	@Autowired
	private Map<String, ResponseFactory> factories;

	private boolean controlManagerDecode = false;// 是否是控制管理程序解码

	/**
	 * @see com.hirain.phm.synapsis.udp.codec.MessageCodec#encode(com.hirain.phm.synapsis.communication.Message)
	 */
	@Override
	public UDPPacket encode(Message<?> message) {
		Object data = message.getData();
		UDPPacket packet = new UDPPacket();
		switch (message.getTarget()) {
		case 2:// 服务管理——>控制管理
			packet.setPort(targetPort);
			controlManagerDecode = true;
			break;
		case 1:// 控制管理——>服务管理
			packet.setPort(sourcePort);
			controlManagerDecode = false;
			break;
		default:
			break;
		}
		packet.setIp(ip);
		if (data instanceof SynapsisRequest) {
			SynapsisRequest request = (SynapsisRequest) data;
			packet.setSid(message.getSid());
			ByteBuffer buffer = ByteBuffer.allocate(12 + request.toBytes().length).order(MessageConstant.MESSAGE_ORDER);
			buffer.put((byte) message.getTarget());
			buffer.put((byte) message.getSource());
			buffer.putShort((short) message.getSid());
			buffer.putInt(0);
			buffer.putInt(request.toBytes().length);
			buffer.put(request.toBytes());
			packet.setData(buffer.array());
		}
		if (data instanceof BoardControlResponseMessage) {
			byte[] response = new byte[] { 0x00, 0x00 };// 启动停止成功
			packet.setSid(message.getSid());
			ByteBuffer buffer = ByteBuffer.allocate(12 + response.length).order(MessageConstant.MESSAGE_ORDER);
			buffer.put((byte) message.getTarget());
			buffer.put((byte) message.getSource());
			buffer.putShort((short) message.getSid());
			buffer.putInt(0);
			buffer.putInt(response.length);
			buffer.put(response);
			packet.setData(buffer.array());
		}
		if (data instanceof BoardStatusResponseMessage) {
			byte[] boardNum = new byte[] { 0x01 };// 板卡状态查询回复
			byte[] boardStatuByte = new byte[128];
			byte[] response = new byte[boardNum.length + boardStatuByte.length];
			System.arraycopy(boardNum, 0, response, 0, boardNum.length);
			System.arraycopy(boardStatuByte, 0, response, boardNum.length, boardStatuByte.length);
			packet.setSid(message.getSid());
			ByteBuffer buffer = ByteBuffer.allocate(12 + response.length).order(MessageConstant.MESSAGE_ORDER);
			buffer.put((byte) message.getTarget());
			buffer.put((byte) message.getSource());
			buffer.putShort((short) message.getSid());
			buffer.putInt(0);
			buffer.putInt(response.length);
			buffer.put(response);
			packet.setData(buffer.array());
		}
		return packet;
	}

	/**
	 * @see com.hirain.phm.synapsis.udp.codec.MessageCodec#decode(com.hirain.phm.synapsis.udp.packet.UDPPacket)
	 */
	@Override
	public Message<?> decode(UDPPacket packet) {
		if (controlManagerDecode) {// 控制管理程序解码
			TransportMessage<SynapsisRequest> message = new TransportMessage<>();
			ByteBuffer buffer = ByteBuffer.wrap(packet.getData()).order(MessageConstant.MESSAGE_ORDER);
			message.setTarget(Byte.toUnsignedInt(buffer.get()));
			message.setSource(Byte.toUnsignedInt(buffer.get()));
			message.setSid(Short.toUnsignedInt(buffer.getShort()));
			message.setCounter(buffer.getInt());
			int length = buffer.getInt();
			byte[] datas = new byte[length];
			buffer.get(datas);
			switch (packet.getSid()) {
			case SidConstant.CONTROL_COMMAND:
				message.setData(new BoardLaunchMessage());
				break;
			case SidConstant.STATUS_COMMAND:
				message.setData(new BoardStatusInquireMessage());
				break;
			default:
				break;
			}
			return message;
		} else {// 服务管理程序解码
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

}
