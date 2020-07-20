/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.storage;

import com.hirain.phm.synapsis.algorithm.domain.StorageData;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Feb 11, 2020 2:20:05 PM
 * @Description
 *              <p>
 *              原始报文存储的servcie
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 11, 2020 zepei.tao@hirain.com 1.0 create file
 */
public interface CacheService {

	/**
	 * 缓存原始报文
	 */
	void cache(StorageData storageData) throws Exception;

	/**
	 * 开始校验当前读取的报文是否是10分钟之内的
	 */
	void startCheck() throws Exception;

	/**
	 * 停止缓存
	 */
	void stop();

	boolean isStart();

	long getCurrentPisition();

}
