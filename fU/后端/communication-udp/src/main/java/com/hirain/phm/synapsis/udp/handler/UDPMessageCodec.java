/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.udp.handler;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.List;

import com.hirain.phm.synapsis.udp.packet.UDPPacket;
import com.hirain.phm.synapsis.udp.util.Constant;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageCodec;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 2, 2019 3:25:15 PM
 * @Description
 *              <p>
 *              UDPPacket和DatagramPacket互转
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 2, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public class UDPMessageCodec extends MessageToMessageCodec<DatagramPacket, UDPPacket> {

	/**
	 * @see io.netty.handler.codec.MessageToMessageCodec#decode(io.netty.channel.ChannelHandlerContext, java.lang.Object, java.util.List)
	 */
	@Override
	protected void decode(ChannelHandlerContext ctx, DatagramPacket packet, List<Object> out) throws Exception {
		if (packet.content().readableBytes() < 12) {
			return;
		}
		ByteBuf buf = packet.content();
		int pos = buf.readerIndex();
		int length = buf.getIntLE(pos + 8);
		if (packet.content().readableBytes() >= length + 12) {
			byte[] bs = new byte[length + 12];
			buf.readBytes(bs);
			UDPPacket msg = decode(bs);
			out.add(msg);
		}
	}

	/**
	 * @param bs
	 * @return
	 */
	private UDPPacket decode(byte[] bs) {
		ByteBuffer buffer = ByteBuffer.wrap(bs).order(Constant.MESSAGE_ORDER);
		UDPPacket msg = new UDPPacket();
		buffer.get();
		buffer.get();
		msg.setSid(Short.toUnsignedInt(buffer.getShort()));
		msg.setData(bs);
		return msg;
	}

	/**
	 * @see io.netty.handler.codec.MessageToMessageCodec#encode(io.netty.channel.ChannelHandlerContext, java.lang.Object, java.util.List)
	 */
	@Override
	protected void encode(ChannelHandlerContext ctx, UDPPacket msg, List<Object> out) throws Exception {
		InetSocketAddress address = new InetSocketAddress(msg.getIp(), msg.getPort());
		DatagramPacket packet = new DatagramPacket(Unpooled.copiedBuffer(msg.getData()), address);
		ctx.writeAndFlush(packet);
	}

}
