/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.train.param;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Nov 18, 2019 5:10:41 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Nov 18, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class TrainCountResponse {

	private int online;

	private int total;

	private int onlineDoor;
}
