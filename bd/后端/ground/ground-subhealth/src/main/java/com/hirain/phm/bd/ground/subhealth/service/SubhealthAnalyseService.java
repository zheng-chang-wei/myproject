/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
/**
 * @Version 1.0   
 * @Author weijia.kong@hirain.com
 * @Created May 30, 2019 3:59:34 PM
 * @Description
 * <p>
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>May 30, 2019     weijia.kong@hirain.com            1.0        create file 
 */
package com.hirain.phm.bd.ground.subhealth.service;

import java.util.List;

import com.hirain.phm.bd.ground.common.event.UnifiedFaultRecord;
import com.hirain.phm.bd.ground.subhealth.domain.SubhealthDetail;
import com.hirain.phm.bd.message.train.SubhealthPacket;

public interface SubhealthAnalyseService {

	public List<SubhealthDetail> analyse(SubhealthPacket packet);

	/**
	 * @param subhealthDetail
	 * @return
	 */
	UnifiedFaultRecord getUnifiedFaultRecord(SubhealthDetail subhealthDetail);

}
