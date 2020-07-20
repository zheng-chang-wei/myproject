/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 23, 2020 11:00:54 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 23, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface TableNameFormatter {

	String getTableName(String project, String train);
}
