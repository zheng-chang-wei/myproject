/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.message;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 4, 2019 3:15:47 PM
 * @Description
 *              <p>
 *              服务管理软件收到的报文的工厂类
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 4, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface ResponseFactory {

	SynapsisResponse create(byte[] datas);
}
