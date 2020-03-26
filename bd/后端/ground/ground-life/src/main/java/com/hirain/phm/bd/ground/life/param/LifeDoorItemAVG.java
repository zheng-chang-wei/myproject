package com.hirain.phm.bd.ground.life.param;

import java.util.Date;

import lombok.Data;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2020年1月8日 下午11:40:23
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2020年1月8日 changwei.zheng@hirain.com 1.0 create file
 */
@Data
public class LifeDoorItemAVG {


	/**
	 * 部件名称
	 */
	private String itemName;

	/**
	 * 评估时间
	 */
	private Date time;

	/**
	 * 剩余寿命平均值
	 */
	private Integer usedLife;

	/**
	 * 总寿命
	 */
	private Integer referenceValue;
}
