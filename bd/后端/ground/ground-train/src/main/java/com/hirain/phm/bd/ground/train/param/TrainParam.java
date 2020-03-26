/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.train.param;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created May 20, 2019 4:24:24 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               May 20, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TrainParam extends TrainParamHeader implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = -4323347721767896285L;

	/**
	 * 车辆当前的状态（恢复接收、停止接收、注销）
	 */
	@ApiModelProperty("车辆当前的状态")
	private Integer trainStatus;

	/**
	 * 车辆在线状态
	 */
	@ApiModelProperty("车辆是否在线")
	private Boolean trainOnline;

}
