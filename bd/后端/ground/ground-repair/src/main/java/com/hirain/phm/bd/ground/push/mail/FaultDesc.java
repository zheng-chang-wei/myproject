/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.push.mail;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jul 1, 2020 4:11:52 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 1, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class FaultDesc {

	private Integer type;

	private Integer code;

	private Integer level;

	private String description;
}
