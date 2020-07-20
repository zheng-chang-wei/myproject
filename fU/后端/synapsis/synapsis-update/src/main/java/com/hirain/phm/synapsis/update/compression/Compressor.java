/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.update.compression;

import java.io.File;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Jul 8, 2020 3:09:49 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 8, 2020 zepei.tao@hirain.com 1.0 create file
 */
public interface Compressor {

	/** 使用UTF-8编码避免压缩中文文件名乱码 */
	static final String CHINESE_CHARSET = "UTF-8";

	/** 文件读取缓冲区大小 */
	static final int CACHE_SIZE = 1024;

	/**
	 * @param sourceFile
	 *            要解压的文件
	 * @param destPath
	 *            目标地址
	 * @throws Exception
	 */
	void deCompress(File sourceFile, String destPath) throws Exception;

}
