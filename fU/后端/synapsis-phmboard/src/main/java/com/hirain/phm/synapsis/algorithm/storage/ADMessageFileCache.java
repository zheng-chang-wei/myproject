/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.storage;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hirain.phm.synapsis.message.MessageConstant;

import lombok.Getter;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Feb 13, 2020 9:50:11 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 13, 2020 zepei.tao@hirain.com 1.0 create file
 */
public class ADMessageFileCache extends MessageFileCache {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	private boolean readOnly;

	@Getter
	private int channelID;

	public ADMessageFileCache(boolean readOnly, long currentPosition) {
		this.readOnly = readOnly;
		getCurrentPosition().addAndGet(currentPosition);
	}

	/**
	 * 初始化
	 * 打开历史文件，获取可读文件句柄和可写文件句柄。
	 * 可读文件指的是历史文件中时间最早的文件。
	 * 可写文件指的是历史文件中时间最晚的文件。
	 */
	public void init(int channelID) {
		this.channelID = channelID;
		final File adFolder = new File(appPath + File.separator + MESSAGE_ROOT, "AD");
		if (!adFolder.exists()) {
			adFolder.mkdirs();
		}
		final File[] children = adFolder.listFiles((FilenameFilter) (dir, name) -> name.endsWith(".cache"));
		if (children != null) {
			final List<File> fileList = Arrays.asList(children);
			Collections.sort(fileList);
			fileList.stream().forEach(f -> {
				setCacheFilePath(f.getAbsolutePath());
			});
		}
		initInChannel();
		if (!readOnly) {// 可读可写
			initOutChannel();
		}
	}

	@Override
	protected File createNewFile() {
		String filepath = appPath + File.separator + MESSAGE_ROOT + File.separator + "AD" + File.separator;
		filepath += String.valueOf(channelID) + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".cache";
		final File file = new File(filepath);
		try {
			file.createNewFile();
			cacheFilePath = file.getAbsolutePath();
		} catch (final IOException e) {
			log.error(e.getMessage(), e);
		}
		return file;
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.storage.MessageFileCache#read()
	 */
	@Override
	public byte[] read() {
		try {
			final FileChannel channel = getInChannel();
			if (channel != null) {
				final ByteBuffer buffer = ByteBuffer.allocate(14);// 4+4+4+2 对应 StorageData
				channel.read(buffer);
				buffer.flip();
				if (!buffer.hasRemaining()) {
					return null;
				}
				final int length = Short.toUnsignedInt(buffer.order(MessageConstant.MESSAGE_ORDER).getShort(12));// 从第12个字节开始的2字节是dataLen
				final ByteBuffer dataBuffer = ByteBuffer.allocate(length);
				channel.read(dataBuffer);
				dataBuffer.flip();
				if (!dataBuffer.hasRemaining()) {
					return null;
				}
				ByteBuffer result = ByteBuffer.allocate(14 + dataBuffer.capacity());
				result.put(buffer.array());
				result.put(dataBuffer.array());
				return result.array();
			}
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

}
