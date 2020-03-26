/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.message.train;

import java.util.List;

import com.hirain.phm.bd.message.header.MessageHeader;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月25日 上午9:39:45
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月25日 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FaultPacket extends MessageHeader {

	private List<FaultMessage> messages;
}
