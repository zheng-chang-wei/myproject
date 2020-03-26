/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.life.param;

import lombok.Data;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created May 24, 2019 10:42:47 AM
 * @Description
 *              <p>
 *              对应车门项点寿命计算结果对象，前端就是根据这个对象的属性值进行计算
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               May 24, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Data
public class DoorItemLifeResult {

	private String itemName;

	private Integer lifeValue;

	// 前端接收的为0-100的值
	private Integer lifePercentage;

	private Integer referenceValue;// 参考值，也暨平均寿命
}
