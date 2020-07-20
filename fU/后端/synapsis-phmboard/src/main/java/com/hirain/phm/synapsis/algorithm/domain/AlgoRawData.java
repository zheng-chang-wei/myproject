/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.domain;

import lombok.Data;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Dec 24, 2019 11:14:47 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 24, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Data
public class AlgoRawData {

	/**
	 * 数据类型，用来标识MVB(byte 0)、ECN(byte 99)、AD(byte 1~N AD数据的通道1~N)
	 */
	private byte dataType;

	/**
	 * 帧计数
	 */
	private int frameIndex;

	private byte[] unix_time;// 4字节

	private byte[] us_time;// 4字节

	private short dataLen;

	private byte[] data;
}
