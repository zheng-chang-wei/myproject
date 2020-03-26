/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.common.event;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Sep 25, 2019 10:08:38 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Sep 25, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class NoSidEvent {

	private String topic;

	private String value;
}
