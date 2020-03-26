/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.flowable;

import com.hirain.phm.bd.ground.maintenance.param.FlowResult;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Nov 12, 2019 1:54:16 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Nov 12, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface FlowableClient {

	FlowResult start();

	FlowResult process(String processId, boolean result);

	FlowResult terminate(String processId);

	FlowResult current(String processId);
}
