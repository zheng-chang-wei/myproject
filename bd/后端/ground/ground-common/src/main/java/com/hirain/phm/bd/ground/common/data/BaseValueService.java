/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.common.data;

import java.util.List;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Feb 20, 2020 3:06:16 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 20, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface BaseValueService {

	List<String> getBaseValue(int projectId, String variable);
}
