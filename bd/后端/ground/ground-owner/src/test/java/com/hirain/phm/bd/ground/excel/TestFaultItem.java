/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.excel;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jul 6, 2020 10:30:04 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 6, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class TestFaultItem {

	private Integer[] fault = new Integer[] { 0, 0, 0, 0, 0 };

	private Integer fault1 = 1;

	private Integer fault2 = 1;

	private Integer fault3 = 1;

	private Integer fault4 = 1;

	private Integer fault5 = 1;
}
