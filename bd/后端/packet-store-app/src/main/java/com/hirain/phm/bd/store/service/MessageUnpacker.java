/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.store.service;

import java.util.List;

import com.hirain.phm.bd.data.bean.DataRecord;
import com.hirain.phm.bd.message.decode.DecodePacket;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 5, 2020 11:22:48 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 5, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface MessageUnpacker {

	List<DataRecord> unpack(DecodePacket packet);
}
