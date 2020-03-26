/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.udp.codec;

import com.hirain.phm.synapsis.communication.Message;
import com.hirain.phm.synapsis.udp.packet.UDPPacket;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 2, 2019 3:46:37 PM
 * @Description
 *              <p>
 *              编解码，Message对象和UDPPacket互转
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 2, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface MessageCodec {

	UDPPacket encode(Message<?> message);

	Message<?> decode(UDPPacket packet);
}
