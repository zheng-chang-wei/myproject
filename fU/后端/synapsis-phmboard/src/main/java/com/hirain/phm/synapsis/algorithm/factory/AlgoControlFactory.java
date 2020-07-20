/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.factory;

import com.hirain.phm.synapsis.algorithm.domain.AlgorithmStatus;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Dec 19, 2019 9:55:36 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 19, 2019     zepei.tao@hirain.com    1.0   create file   
 */
public interface AlgoControlFactory {

	/**
	 * 启动算法进程
	 */
	void launch(String filePath) throws Exception;

	/**
	 * 停止算法进程
	 */
	void shut(int pid) throws Exception;

	/**
	 * 查询算法进程是否还在运行
	 */
	AlgorithmStatus statusInquire(int pid) throws Exception;
}
