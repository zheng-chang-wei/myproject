/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.data.performance;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Random;

import org.apache.commons.io.FileUtils;

import com.hirain.phm.synapsis.compress.QuickLzSupport;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 17, 2020 2:10:48 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 17, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class FilePrepare {

	/**
	 * 
	 */
	public static ByteBuffer getDataBuffer() {
		ByteBuffer buffer = ByteBuffer.allocate(26 * 30000).order(ByteOrder.LITTLE_ENDIAN);
		Random random = new Random();
		for (int i = 0; i < 30000; i++) {
			buffer.putInt(i + 1);
			long millis = System.currentTimeMillis();
			long second = millis / 1000;
			long micro = (millis - second * 1000) * 1000;
			buffer.putInt((int) second);
			buffer.putInt((int) micro);
			buffer.putShort((short) 12);
			buffer.putInt(random.nextInt(10) + 10);
			buffer.putDouble(random.nextDouble());
		}
		return buffer;
	}

	private static String FILE_ROOT = System.getProperty("user.dir") + File.separator + "test-data";

	public static void main(String[] args) throws IOException {
		wirteFile(FILE_ROOT + "\\201912\\18\\150000\\MVB_01_20191218_150000.txt");
		wirteFile(FILE_ROOT + "\\201912\\18\\150000\\MVB_01_20191218_150500.txt");
		wirteFile(FILE_ROOT + "\\201912\\18\\160000\\MVB_01_20191218_160000.txt");
	}

	/**
	 * @param filepath
	 * @throws IOException
	 */
	private static void wirteFile(String filepath) throws IOException {
		ByteBuffer buffer = FilePrepare.getDataBuffer();
		QuickLzSupport support = new QuickLzSupport();
		byte[] compress = support.compress(buffer.array());
		FileUtils.writeByteArrayToFile(new File(filepath), compress);
	}
}
