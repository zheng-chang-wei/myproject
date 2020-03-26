/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/

package com.hirain.phm.bd.ground.realtime.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Version 1.0
 * @Author weijia.kong@hirain.com
 * @Created May 17, 2019 3:02:04 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               May 17, 2019 weijia.kong@hirain.com 1.0 create file
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonitorOption {

	private String project;

	private String train;

	private int carID;

	private int doorID;

}
