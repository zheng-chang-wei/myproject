/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.message;

import com.hirain.phm.synapsis.message.SynapsisResponse;

/**
 * @Version 1.0   
 * @Author zepei.tao@hirain.com  
 * @Created Dec 14, 2019 2:34:31 PM  
 * @Description
 * <p>  控制管理-->算法管理   版本查询
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>Dec 14, 2019     zepei.tao@hirain.com             1.0        create file 
 */
public class VersionInquireMessage implements SynapsisResponse {

	/** 
	 * @see com.hirain.phm.synapsis.message.SynapsisResponse#parse(byte[])
	 */
	@Override
	public void parse(byte[] datas) {

	}

}
