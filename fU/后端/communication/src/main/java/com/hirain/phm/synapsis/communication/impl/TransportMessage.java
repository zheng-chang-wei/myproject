/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.communication.impl;

import com.hirain.phm.synapsis.communication.Message;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 3, 2019 5:03:53 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 3, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@NoArgsConstructor
public class TransportMessage<T> implements Message<T> {

	private int sid;

	private int source;

	private int target;

	private int counter;

	private T data;

	/**
	 * @param sid
	 * @param source
	 * @param target
	 * @param data
	 */
	public TransportMessage(int sid, int source, int target, T data) {
		this.sid = sid;
		this.source = source;
		this.target = target;
		this.data = data;
	}

}
