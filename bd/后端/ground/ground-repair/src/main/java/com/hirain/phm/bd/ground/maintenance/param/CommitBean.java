/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.param;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月30日 下午2:46:57
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月30日 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class CommitBean<T> {

	private Long sheetId;

	private boolean result;

	private T object;
}
