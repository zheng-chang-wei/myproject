/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.domain;

import lombok.Getter;

/**
 * @Version 1.0   
 * @Author zepei.tao@hirain.com  
 * @Created Dec 14, 2019 2:30:04 PM  
 * @Description
 * <p>   算法运行状态枚举类：idle空闲、working工作中、error错误
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>Dec 14, 2019     zepei.tao@hirain.com             1.0        create file 
 */
public enum AlgorithmStatus {
	IDLE(0), WORKING(1), STOP(2), ERROR(3);

	@Getter
	private int code;

	private AlgorithmStatus(int code) {
		this.code = code;
	}
}
