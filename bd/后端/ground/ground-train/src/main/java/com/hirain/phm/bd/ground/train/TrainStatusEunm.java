/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.train;

import lombok.Getter;

/**
 * @Version 1.0   
 * @Author zepei.tao@hirain.com  
 * @Created May 20, 2019 7:13:58 PM  
 * @Description
 * <p>   控制列车和地面端通信状态的枚举类
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>May 20, 2019     zepei.tao@hirain.com             1.0        create file 
 */
public enum TrainStatusEunm {

	ACTIVATE(1, "开始接收"),

	DISCONNECT(2, "停止接收"),

	DEACTIVATE(3, "注销");

	@Getter
	private int status;

	@Getter
	private String description;

	TrainStatusEunm(int status, String description) {
		this.status = status;
		this.description = description;
	}

}
