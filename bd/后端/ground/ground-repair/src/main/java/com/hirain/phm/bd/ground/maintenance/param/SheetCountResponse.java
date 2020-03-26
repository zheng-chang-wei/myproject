/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.param;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Nov 15, 2019 2:52:21 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Nov 15, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class SheetCountResponse {

	private int newSheets;

	private int handled;

	private int unHandled;

	private int unHandledBeforeMonth;
}
