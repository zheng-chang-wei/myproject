/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.board.domain;

import lombok.Getter;

/**
 * @Version 1.0   
 * @Author zepei.tao@hirain.com  
 * @Created Dec 4, 2019 5:47:01 PM  
 * @Description
 * <p>
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>Dec 4, 2019     zepei.tao@hirain.com             1.0        create file 
 */
public enum BoardStartResult {
	SUCCESSFUL(0), //成功
	CONFIG_FAULT(1), //配置失败
	START_FAULT(2);//启动失败

	@Getter
	private int code;

	private BoardStartResult(int code) {
		this.code = code;
	}

}
