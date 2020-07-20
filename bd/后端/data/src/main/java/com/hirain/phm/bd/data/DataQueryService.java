/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.data;

import java.util.List;

import com.hirain.phm.bd.data.bean.DataParam;
import com.hirain.phm.bd.data.bean.DataRecord;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 4, 2020 5:36:06 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 4, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface DataQueryService {

	List<DataRecord> select(DataParam param);

	int count(DataParam param);

}
