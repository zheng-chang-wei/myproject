/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
/**
 * @Version 1.0   
 * @Author weijia.kong@hirain.com
 * @Created Jun 13, 2019 11:52:41 AM
 * @Description
 * <p>
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>Jun 13, 2019     weijia.kong@hirain.com            1.0        create file 
 */
package com.hirain.phm.bd.message.train;

import com.hirain.phm.bd.message.header.MessageHeader;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class WillMessage extends MessageHeader {

	/*
	 * 车辆上线/下线标识 true:上线 false:下线
	 */
	private Boolean on;

}
