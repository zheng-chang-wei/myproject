/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.data.hive.file;

import java.util.List;

import com.hirain.phm.bd.data.bean.DataRecord;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 11, 2020 10:41:46 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 11, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface FileWriter {

	void write(List<DataRecord> records);

	/**
	 * @param tableName
	 */
	void init(String tableName);
}
