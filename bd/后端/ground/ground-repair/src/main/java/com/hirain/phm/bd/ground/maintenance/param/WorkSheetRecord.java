/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.param;

import com.hirain.phm.bd.ground.maintenance.domain.WorkSheet;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年7月17日 下午6:16:53
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年7月17日 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class WorkSheetRecord {

	private WorkSheet sheet;

	private boolean option;
}
