/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.push.service;

import com.hirain.phm.bd.ground.common.event.UnifiedFaultRecord;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Oct 18, 2019 1:27:33 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Oct 18, 2019 jianwen.xin@hirain.com 1.0 create file
 */

public interface PushFaultService {

	public void push(UnifiedFaultRecord record, long sheetId);
}
