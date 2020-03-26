/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.realtime.service;

import com.hirain.phm.bd.message.decode.DecodePacket;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月10日 下午5:52:42
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月10日 jianwen.xin@hirain.com 1.0 create file
 */
public interface IDecoderService {

	Object decode(String msg);

	DecodePacket decode(byte[] datas);
}
