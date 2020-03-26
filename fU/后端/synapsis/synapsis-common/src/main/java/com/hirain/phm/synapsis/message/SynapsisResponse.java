/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.message;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 4, 2019 2:02:10 PM
 * @Description
 *              <p>
 *              服务管理软件收到的报文基类
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 4, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface SynapsisResponse {

	/**
	 * 生成报文对象
	 * 
	 * @param datas
	 */
	void parse(byte[] datas);
}
