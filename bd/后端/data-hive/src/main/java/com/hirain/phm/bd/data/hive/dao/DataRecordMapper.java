/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.data.hive.dao;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 5, 2020 7:06:29 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 5, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface DataRecordMapper {

	/**
	 * @param tableName
	 */
	void createTable(String tableName);

	void loadHdfsFile(String tableName, String filepath, String partition);

}
