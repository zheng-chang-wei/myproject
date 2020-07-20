/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.common.event;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月13日 上午11:48:43
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月13日 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@AllArgsConstructor
public class MessageEvent {

	private String key;

	private byte[] datas;

}
