/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.board.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Version 1.0   
 * @Author zepei.tao@hirain.com  
 * @Created Dec 3, 2019 11:04:44 AM  
 * @Description
 * <p>
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>Dec 3, 2019     zepei.tao@hirain.com             1.0        create file 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WirelessBoard extends Board {

	/**
	 * 4G信号强度打分
	 */
	private int sigQuality4G;

	/**
	 * wifi信号强度打分
	 */
	private int sigQualityWifi;
}
