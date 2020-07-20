/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.storage;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import lombok.Setter;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Feb 12, 2020 1:42:05 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 12, 2020 zepei.tao@hirain.com 1.0 create file
 */
public abstract class MessageFileCache {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	protected static final String MESSAGE_ROOT = "cache";

	protected FileOutputStream fos;

	protected FileChannel outChannel;// 写文件通道

	protected FileInputStream fis;

	protected String appPath = "/home/root/Hirain/Hi_opt/app";

	@Getter
	protected FileChannel inChannel;// 读文件通道

	@Getter
	@Setter
	protected AtomicLong currentPosition = new AtomicLong();

	@Setter
	protected String cacheFilePath;

	protected final ExecutorService writeExecutor = Executors
			.newSingleThreadExecutor(r -> new Thread(r, MessageFileCache.class.getName() + "-write"));

	public void close() {
		try {
			fis.close();
			inChannel.close();
			fos.close();
			outChannel.close();
		} catch (IOException e) {
		}
	}

	/**
	 * 向可写文件中追加报文内容
	 */
	public void write(byte[] payload) {
		writeExecutor.submit(() -> {
			final ByteBuffer buffer = ByteBuffer.allocate(payload.length);
			buffer.put(payload);
			buffer.flip();
			try {
				outChannel.write(buffer);
			} catch (final IOException e) {
				log.error(e.getMessage(), e);
			}
		});
	}

	/**
	 * 初始化可读通道
	 */
	protected void initInChannel() {
		if (cacheFilePath != null) {
			try {
				fis = new FileInputStream(cacheFilePath);
				inChannel = fis.getChannel();
				inChannel.position(currentPosition.get());
			} catch (final IOException e) {
				log.error(e.getMessage(), e);
			}
		} else {
			final File file = createNewFile();
			try {
				fis = new FileInputStream(file);
				inChannel = fis.getChannel();
				inChannel.position(currentPosition.get());
			} catch (final IOException e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 初始化可写通道
	 */
	protected void initOutChannel() {
		try {
			fos = new FileOutputStream(cacheFilePath, true);
			outChannel = fos.getChannel();
		} catch (final FileNotFoundException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 从可读文件中读取一条报文数据
	 * 
	 * @return
	 */
	public abstract byte[] read();

	protected abstract File createNewFile();

}
