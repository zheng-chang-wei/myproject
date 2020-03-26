/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
/**
 * @Version 1.0   
 * @Author weijia.kong@hirain.com
 * @Created Jun 13, 2019 10:30:24 AM
 * @Description
 * <p>
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>Jun 13, 2019     weijia.kong@hirain.com            1.0        create file 
 */
package com.hirain.phm.bd.ground.train.param;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TrainParamHeader implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8322891894633000880L;

	private String project;

	/**
	 * 列车编号
	 */
	@ApiModelProperty("列车编号")
	private String trainNo;

}
