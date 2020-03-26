/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.store;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月20日 下午3:02:20
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月20日 jianwen.xin@hirain.com 1.0 create file
 */
public class CapacityTest {

	static String msg = "0C166000001504000C04210421013B010CFA0009020100000C0000000000243C";

	static String line_separator = System.getProperty("line.separator");

	public static void main(String[] args) throws IOException {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 100; i++) {
			sb.append("20190220150411").append(",");
			sb.append("01").append(",");
			sb.append("02").append(",");
			sb.append(msg).append(line_separator);
		}
		byte[] bytes = sb.toString().getBytes();
		ByteBuffer buffer = ByteBuffer.wrap(bytes);
		RandomAccessFile file = new RandomAccessFile("E:\\test.rt", "rw");
		FileChannel channel = file.getChannel();
		channel.write(buffer);
		file.close();
	}
}
