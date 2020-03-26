/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.transform.message;

import org.springframework.context.ApplicationEvent;

import com.hirain.phm.bd.message.TrainPacket;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月8日 下午5:55:24
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月8日 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MessageEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	private String topic;

	private TrainPacket packet;

	/**
	 * @param source
	 * @param topic
	 */
	public MessageEvent(Object source, TrainPacket packet, String topic) {
		super(source);
		this.packet = packet;
		this.topic = topic;
	}

}
