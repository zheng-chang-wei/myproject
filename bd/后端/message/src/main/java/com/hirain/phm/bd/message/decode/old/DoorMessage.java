/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.message.decode.old;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月19日 下午4:34:34
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月19日 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Deprecated
public class DoorMessage {

	private RunDataFrame frame;

	private boolean debug;
}
