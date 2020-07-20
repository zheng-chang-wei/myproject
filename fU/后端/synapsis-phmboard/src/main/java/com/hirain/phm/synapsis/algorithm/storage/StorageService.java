/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.storage;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Feb 19, 2020 4:20:28 PM
 * @Description
 *              <p>
 *              存储原始数据
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 19, 2020 zepei.tao@hirain.com 1.0 create file
 */
public interface StorageService {

	/**
	 * 初始化操作
	 */
	void init(String folderName, String fileName);

	/**
	 * 存储原始报文
	 * 
	 * @param warningTime
	 *            报警时戳
	 */
	void storage(long warningTime);

	/**
	 * 停止存储
	 */
	void shutdown();

}
