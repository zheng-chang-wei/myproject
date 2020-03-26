/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.data.hive.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 11, 2020 11:35:14 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 11, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoadFileEvent {

	private String tableName;

	private String filepath;
}
