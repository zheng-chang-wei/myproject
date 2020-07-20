/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.data;

import com.hirain.phm.bd.data.bean.DataParam;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 23, 2020 2:06:17 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 23, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface DataDefinitionService {

	String[] selectRange(DataParam param);

	void delete(DataParam param);
}
