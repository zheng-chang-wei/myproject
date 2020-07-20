/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm;

import java.nio.ByteBuffer;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.algorithm.domain.PHMAlgoResult;
import com.hirain.phm.synapsis.algorithm.message.AlgoControlMessage;
import com.hirain.phm.synapsis.algorithm.message.AlgoControlResponseMessage;
import com.hirain.phm.synapsis.algorithm.message.AlgoResultMessage;
import com.hirain.phm.synapsis.algorithm.message.AlgoResultResponseMessage;
import com.hirain.phm.synapsis.algorithm.message.StatusInquireMessage;
import com.hirain.phm.synapsis.algorithm.message.StatusResponseMessage;
import com.hirain.phm.synapsis.algorithm.message.VersionInquireMessage;
import com.hirain.phm.synapsis.algorithm.message.VersionResponseMessage;
import com.hirain.phm.synapsis.algorithm.util.PHMProgram;
import com.hirain.phm.synapsis.algorithm.util.SidConstant;
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
@Profile("test")
public class TestCodec implements MessageCodec {

	@Value("${synapsis.local.control_port}")
	private int targetPort;

	@Value("${synapsis.control.port}")
	private int sourcePort;

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
		case 4:// 算法管理——>控制管理
			packet.setPort(sourcePort);
			controlManagerDecode = true;
			break;
		case 3:// 控制管理——>算法管理
			packet.setPort(targetPort);
			controlManagerDecode = false;
			break;
		default:
			break;
		}
		packet.setIp("127.0.0.1");
		if (data instanceof VersionInquireMessage || data instanceof StatusInquireMessage) {
			packet.setSid(message.getSid());
			ByteBuffer buffer = ByteBuffer.allocate(12).order(MessageConstant.MESSAGE_ORDER);
			buffer.put((byte) message.getTarget());
			buffer.put((byte) message.getSource());
			buffer.putShort((short) message.getSid());
			buffer.putInt(0);// TODO 计数器
			buffer.putInt(0);
			packet.setData(buffer.array());
		}
		if (data instanceof AlgoResultResponseMessage) {
			packet.setSid(message.getSid());
			ByteBuffer buffer = ByteBuffer.allocate(12 + 1).order(MessageConstant.MESSAGE_ORDER);
			buffer.put((byte) message.getTarget());
			buffer.put((byte) message.getSource());
			buffer.putShort((short) message.getSid());
			buffer.putInt(0);// TODO 计数器
			buffer.putInt(1);
			buffer.put((byte) 0);
			packet.setData(buffer.array());
		}
		if (data instanceof AlgoControlMessage) {
			packet.setSid(message.getSid());
			ByteBuffer buffer = ByteBuffer.allocate(12 + 1).order(MessageConstant.MESSAGE_ORDER);
			buffer.put((byte) message.getTarget());
			buffer.put((byte) message.getSource());
			buffer.putShort((short) message.getSid());
			buffer.putInt(0);// TODO 计数器
			buffer.putInt(1);
			buffer.put((byte) 1);
			packet.setData(buffer.array());
		}
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
			case SidConstant.PHM_VERSION_COMMAND:
				VersionResponseMessage response1 = new VersionResponseMessage();
				response1.setVersion("1.0.0");
				message.setData(response1);
				break;
			case SidConstant.PHM_ALGOCONTROL_COMMAND:
				AlgoControlResponseMessage response2 = new AlgoControlResponseMessage();
				response2.setResult(1);
				message.setData(response2);
				break;
			case SidConstant.PHM_ALGOSTATUS_COMMAND:
				StatusResponseMessage response4 = new StatusResponseMessage();
				//				response4.setStatus(AlgorithmStatus.IDLE);
				message.setData(response4);
				break;
			case SidConstant.PHM_ALGORESULT_COMMAND:
				AlgoResultMessage response3 = new AlgoResultMessage();
				response3.setPhmAlgoResult(new PHMAlgoResult());
				message.setData(response3);
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
			ResponseFactory factory = factories.get(PHMProgram.values()[message.getSource()].name() + packet.getSid());
			SynapsisResponse response = factory.create(datas);
			message.setData(response);
			return message;
		}
	}

}
