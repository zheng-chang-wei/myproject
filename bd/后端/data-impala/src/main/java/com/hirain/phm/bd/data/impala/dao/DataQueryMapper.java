/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.data.impala.dao;

import java.util.List;

import com.hirain.phm.bd.data.bean.DataParam;
import com.hirain.phm.bd.data.bean.DataRecord;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 6, 2020 1:55:54 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 6, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface DataQueryMapper {

	/**
	 * @param tableName
	 * @param param
	 * @return
	 */
	int count(String tableName, DataParam param);

	/**
	 * @param tableName
	 * @param param
	 * @return
	 */
	List<DataRecord> select(String tableName, DataParam param);

	/**
	 * @param tableName
	 * @param partition
	 */
	void refresh(String tableName, String partition);

}
