/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.message.decode;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月17日 下午3:22:53
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月17日 jianwen.xin@hirain.com 1.0 create file
 */
public interface IDoorStateCalculator {

	/**
	 * @param frame
	 * @return
	 */
	String calculate(RunDataFrame frame);

}
