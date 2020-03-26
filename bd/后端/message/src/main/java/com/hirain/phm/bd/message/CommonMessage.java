/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.message;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月8日 下午5:08:22
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月8日 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@NoArgsConstructor
public class CommonMessage {

	private byte[] datas;

	private boolean debug;

	private int milli;

	private int year;

	private int month;

	/**
	 * @param datas
	 * @param debug
	 */
	public CommonMessage(byte[] datas, boolean debug) {
		this.datas = datas;
		this.debug = debug;
	}

}
