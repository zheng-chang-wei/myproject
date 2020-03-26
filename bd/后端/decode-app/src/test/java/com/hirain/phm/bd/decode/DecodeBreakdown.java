package com.hirain.phm.bd.decode;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.hirain.phm.bd.common.ZipUtil;
import com.hirain.phm.bd.common.serialize.JsonUtil;
import com.hirain.phm.bd.message.CarriagePacket;
import com.hirain.phm.bd.message.CommonMessage;
import com.hirain.phm.bd.message.DoorPacket;
import com.hirain.phm.bd.message.TrainPacket;
import com.hirain.phm.bd.message.decode.DecodePacket;
import com.hirain.phm.bd.message.decode.KeyValueDecoder;
import com.hirain.phm.bd.message.decode.RunDataFrame;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DecodeBreakdown {

	static KeyValueDecoder decoder = new KeyValueDecoder();

	public static void main(String[] args) {
		String path = "D:\\data\\latest-1.cache";
		FileInputStream stream = null;
		FileChannel channel = null;
		try {
			stream = new FileInputStream(path);
			channel = stream.getChannel();
			long length = channel.size();
			// channel.position(861832);
			int count = 0;
			while (channel.position() < length) {
				try {
					// if (count >= 50712) {
					// System.out.println("break down");
					// }
					ByteBuffer buffer = ByteBuffer.allocate(4);
					channel.read(buffer);
					buffer.flip();
					int len = buffer.getInt();
					if (len > channel.size() - channel.position()) {
						continue;
					}
					ByteBuffer dataBuffer = ByteBuffer.allocate(len);
					channel.read(dataBuffer);
					dataBuffer.flip();
					// if (len == 1) {
					// continue;
					// }
					log.info("{},{}", len, String.valueOf(channel.position()));
					byte[] compress = dataBuffer.array();
					byte[] uncompress = ZipUtil.uncompress(compress);
					String value = new String(uncompress, Charset.forName("utf-8"));
					List<RunDataFrame> frames = new ArrayList<>();
					TrainPacket packet = JsonUtil.fromString(value, TrainPacket.class);
					if (packet == null || packet.getPackets() == null) {
						log.error("packet==null||packet.getPackets()==null");
						continue;
					}
					for (CarriagePacket cpacket : packet.getPackets()) {
						for (DoorPacket dpacket : cpacket.getPackets()) {
							for (CommonMessage message : dpacket.getMessages()) {
								RunDataFrame frame = decoder.decode(message);
								if (frame != null) {
									frame.setDebug(message.isDebug());
									frames.add(frame);
								} else {
									log.error("frame==null");
								}
							}
						}
					}
					DecodePacket decodePacket = new DecodePacket(0x21, packet.getProject(), packet.getTrain());
					decodePacket.setFrames(frames);
					decodePacket.setKeys(KeyValueDecoder.keys);
					// send(record.topic(), record.key(), decodePacket);
					// log.info("{},{}", record.topic(), record.key());
					log.info("{},{}", count++, frames.get(0).getValues().get(58));
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				if (stream != null) {
					channel.close();
					stream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
