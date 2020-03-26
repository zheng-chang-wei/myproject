/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.data.hive.event;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 12, 2020 10:26:33 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 12, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@AllArgsConstructor
public class RefreshMetadataEvent {

	private String tableName;

	private String partition;
}
