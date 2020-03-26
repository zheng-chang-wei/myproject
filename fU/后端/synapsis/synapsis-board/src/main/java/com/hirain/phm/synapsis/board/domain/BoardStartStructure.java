/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.board.domain;

import lombok.Data;

/**
 * @Version 1.0   
 * @Author zepei.tao@hirain.com  
 * @Created Dec 4, 2019 5:43:51 PM  
 * @Description
 * <p>
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>Dec 4, 2019     zepei.tao@hirain.com             1.0        create file 
 */
@Data
public class BoardStartStructure {

	private String cardIP;

	private int slotID;

	private BoardStartResult result;
}
