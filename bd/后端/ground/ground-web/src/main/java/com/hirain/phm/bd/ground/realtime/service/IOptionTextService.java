/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
/**
 * @Version 1.0   
 * @Author weijia.kong@hirain.com
 * @Created May 20, 2019 6:48:21 PM
 * @Description
 * <p>
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>May 20, 2019     weijia.kong@hirain.com            1.0        create file 
 */
package com.hirain.phm.bd.ground.realtime.service;

import com.hirain.phm.bd.ground.realtime.domain.MonitorOption;
import com.hirain.phm.bd.message.decode.DecodePacket;

public interface IOptionTextService {

	public String getKey(DecodePacket packet);
	
	public String getKey(MonitorOption option);
	
}
