/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.message;

import lombok.Data;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec 2020/4/28 16:12
 * @Description
 *              <p>
 *              接收的组播数据对象，对应通信协议中CPU&MVB、CPU&ECN MVB、ECN订阅数据报文
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2020/4/28 zepei.tao@hirain.com 1.0 create file
 */
@Data
public class MulticastAlgoMessage {

	private byte target;

	private byte source;

	private short frameCommd;

	private int counter;

	private int length;

	private byte[] data;
}
