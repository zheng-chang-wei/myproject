/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.message.data;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 12, 2020 10:43:35 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 12, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class MetaDataEvent {

	private int sid = 0x22;

	private String tableName;

	private String partition;

	public MetaDataEvent(String tableName, String partition) {
		this.tableName = tableName;
		this.partition = partition;
	}
}
