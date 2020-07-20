/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.domain;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.concurrent.atomic.AtomicBoolean;

import com.hirain.phm.synapsis.algorithm.storage.ECNMessageFileCache;

import lombok.Data;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec May 18, 2020 12:09:33 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               May 18, 2020 zepei.tao@hirain.com 1.0 create file
 */
@Data
public class ECNStorageMessage {

	private ECNMessageFileCache cache;

	/**
	 * 带时戳的文件夹
	 */
	private File folder;

	/**
	 * 分析数据文件
	 */
	private File file;

	protected FileChannel out;

	protected FileOutputStream fos;

	private AtomicBoolean storaging = new AtomicBoolean(false);

	private long currentPosition;

	public ECNStorageMessage(long currentPosition) {
		this.currentPosition = currentPosition;
	}

	public void init(String folderPath, String fileName) throws Exception {
		cache = new ECNMessageFileCache(true, currentPosition);
		cache.init();
		folder = new File(folderPath);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		String filePath = folder.getAbsolutePath() + File.separator + fileName;
		file = new File(filePath);
		file.createNewFile();
	}

	/**
	 * 初始化可写通道
	 */
	public void initOutChannel() throws Exception {
		fos = new FileOutputStream(file.getAbsolutePath(), true);
		out = fos.getChannel();
	}

	public void close() throws Exception {
		if (fos != null && out != null) {
			fos.close();
			out.close();
		}
	}
}
